/**
*@Author: sam
*@Date: 2018年3月12日
*@Copyright: 2018  All rights reserved.
*/
package org.cloud.core.shiro.cache;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheManager implements CacheManager , Destroyable{
	
	private String cacheKeyPrefix="cache";
	@Resource
    private RedisTemplate<String, Object> redisTemplate;

	@Override
    public void destroy() throws Exception {

        Set<String> redisKeys = redisTemplate.keys(this.cacheKeyPrefix + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
    }
	
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisShiroCache<K, V>(cacheKeyPrefix+"_"+name, redisTemplate);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

	
}

