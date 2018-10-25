package org.cloud.core.shiro.session;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.cloud.core.app.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * 基于redis的sessionDao，缓存共享session
 */
public class SystemSessionDao extends EnterpriseCacheSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(SystemSessionDao.class);
    
    @Resource
    private RedisTemplate<String, Session> redisTemplate;

	@Autowired
    private StringRedisTemplate  stringRedisTemplate;
	
    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
    	
    	Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
        redisTemplate.opsForValue().set(AppConst.SHIRO_SESSION_ID + "_" + sessionId.toString(), session);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            
        	session = redisTemplate.opsForValue().get(AppConst.SHIRO_SESSION_ID + "_" + sessionId);
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.debug("获取session:{}", session.getId());
        
        if (session == null || session.getId() == null) {
            logger.info("session or sessionId is null");
            
            return;
        }
        //!!session.setAttribute("FORCE_LOGOUT", session.getAttribute("FORCE_LOGOUT"));
        redisTemplate.opsForValue().set(AppConst.SHIRO_SESSION_ID + "_" + session.getId(), session, session.getTimeout(), TimeUnit.MILLISECONDS);
        
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
        
        //==
        String sessionId = session.getId().toString();
        String upmsType =StringUtils.defaultString((String)session.getAttribute(AppConst.SSO_TYPE));
        if ("client".equals(upmsType)) {
            // 删除局部会话和同一code注册的局部会话
            
        	String code =stringRedisTemplate.opsForValue().get(AppConst.CLIENT_SESSION_ID + "_" + sessionId);
        	
            redisTemplate.delete(AppConst.CLIENT_SESSION_ID + "_" + sessionId);
            redisTemplate.boundListOps(AppConst.CLIENT_SESSION_IDS + "_" + code).remove(1, session);
        }
        if ("server".equals(upmsType)) {
            
        	// 当前全局会话code
            String code =stringRedisTemplate.opsForValue().get(AppConst.SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            stringRedisTemplate.delete(AppConst.SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            stringRedisTemplate.delete(AppConst.SERVER_CODE + "_" + code);
            
            // 清除所有局部会话
            Long size=stringRedisTemplate.boundListOps(AppConst.CLIENT_SESSION_IDS + "_" + code).size();
            List<String> clientSessionIds = stringRedisTemplate.boundListOps(AppConst.CLIENT_SESSION_IDS + "_" + code).range(0, size);
            for (String clientSessionId : clientSessionIds) {
                
                stringRedisTemplate.delete(AppConst.CLIENT_SESSION_ID + "_" + clientSessionId);
                stringRedisTemplate.boundListOps(AppConst.CLIENT_SESSION_IDS + "_" + code).remove(1, clientSessionId);
            }
            logger.debug("当前code={}，对应的注册系统个数：{}个", code, clientSessionIds.size());
        }
        // 删除session
        redisTemplate.delete(AppConst.SHIRO_SESSION_ID + "_" + session.getId());
        
        
        
    }
    
    /**
     * 获取会话列表
     * @param offset
     * @param limit
     * @return
     */
    public Map<String,Object> getActiveSessions(int offset, int limit) {
        Map<String,Object> sessions = new HashMap<>();
        
        // 获取在线会话总数
        long total =  stringRedisTemplate.boundListOps(AppConst.SERVER_SESSION_IDS).size();
        // 获取当前页会话详情
        List<String> ids =stringRedisTemplate.boundListOps(AppConst.SERVER_SESSION_IDS).range(offset, (offset + limit - 1));
        
        List<Session> rows = new ArrayList<>();
        for (String id : ids) {
            Session session = redisTemplate.opsForValue().get(AppConst.SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == session) {
                
            	stringRedisTemplate.boundListOps(AppConst.SERVER_SESSION_IDS).remove(1, id);
                total = total - 1;
                continue;
            }
             rows.add(session);
        }
        sessions.put("total", total);
        sessions.put("rows", rows);
        return sessions;
    }

    /**
     * 强制退出
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
        	
        	Session oneSession= redisTemplate.opsForValue().get(AppConst.SHIRO_SESSION_ID + "_" + sessionId);
        	
        	if(oneSession!=null){
        		
        		oneSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
        		redisTemplate.opsForValue().set(AppConst.SHIRO_SESSION_ID + "_" + sessionId, oneSession, oneSession.getTimeout(), TimeUnit.MILLISECONDS);
        	       
        	}

        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     
    public void updateStatus(Serializable sessionId, SystemSession.OnlineStatus onlineStatus) {
        SystemSession session = (SystemSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(AppConst.SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }
     */

}
