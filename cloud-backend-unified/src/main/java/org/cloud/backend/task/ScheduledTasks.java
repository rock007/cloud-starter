
package org.cloud.backend.task;

import org.apache.shiro.session.Session;
import org.cloud.core.shiro.session.SystemSessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private SystemSessionDao sessionDAO;
	
    //@Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
    }

    //每10分钟执行一次
    @Scheduled(cron = "0 */30 *  * * * ")
    public void checkSessionTimeout(){
    	
    	logger.debug("check session timeout :" + dateFormat ().format (new Date ()));
        
        try  {
        	
        	Map sessions= sessionDAO.getActiveSessions(0, 100);
        	
        	List<Session> rows=(List<Session>) sessions.get("rows");
        	
        	for(Session s:rows){
        		
        		logger.debug(s.getHost() +"  startTimestamp:"+s.getStartTimestamp()+" lastAccessTime:"+s.getLastAccessTime());
        		
        	}
        	
        }catch(Exception ex){
        	
        	logger.error("exception:", ex);
        }

    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
    
}