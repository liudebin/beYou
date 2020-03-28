package read.write.talk.beyou.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.data.service.AyUserService;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 描述：监听器
 * @author Ay
 * @date   2017/11/4
 */
//@Repository
public class AyUserApplicationListener implements ApplicationListener {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyUserService ayUserService;
    private static final String ALL_USER = "ALL_USER_LIST";

    Logger logger = LogManager.getLogger(this.getClass());
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("AyUserApplicationListener {}" , event);
    }


}
