package read.write.talk.beyou.quartz;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.data.service.AyUserService;
import read.write.talk.beyou.mail.SendJunkMailService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：定时器类
 */
@Component
@Configurable
@EnableScheduling
public class GroovyQuartz {

    //日志对象
    private static final Logger logger = LogManager.getLogger(GroovyQuartz.class);


    @Resource
    private AyUserService ayUserService;

    //每5秒执行一次
    @Scheduled(cron = "*/5 * *  * * * ")
    public void reportCurrentByCron(){
        List<AyUserDTO> userList = ayUserService.findAll();
        if (userList == null || userList.size() <= 0) return;

        logger.info("定时器运行了!!!");
    }

}
