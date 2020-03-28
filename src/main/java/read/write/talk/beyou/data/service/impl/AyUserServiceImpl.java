package read.write.talk.beyou.data.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import read.write.talk.beyou.data.repository.AyUserRepository;
import read.write.talk.beyou.data.service.AyUserService;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.error.BusinessException;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 描述：用户服务层实现类
 * @author 阿毅
 * @date   2017/10/14
 */
@Transactional
@Service
public class AyUserServiceImpl implements AyUserService {

    @Resource(name = "ayUserRepository")
    private AyUserRepository ayUserRepository;

//    @Reference(version = "1.0")
//    public AyUserDubboService ayUserDubboService;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String ALL_USER = "ALL_USER_LIST";

//    @Resource
//    private AyUserDao ayUserDao;

    @Override
    public Long findUserTotalNum() {
        return ayUserRepository.count();
    }

    @Override
    public AyUserDTO findByNameAndPassword(String name, String password) {
//        return ayUserDao.findByNameAndPassword(name, password);
        return null;//bingo
    }

    @Override
    @Retryable(value= {BusinessException.class},maxAttempts = 5,backoff = @Backoff(delay = 5000,multiplier = 2))
    public AyUserDTO findByNameAndPasswordRetry(String name, String password) {
        System.out.println("[findByNameAndPasswordRetry] 方法失败重试了！");
        throw new BusinessException();
//        return null;//bingo
    }

    @Override
    public AyUserDTO findById(String id){
        //step.1  查询Redis缓存中的所有数据
        List<AyUserDTO> ayUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if(ayUserList != null && ayUserList.size() > 0){
            for(AyUserDTO user : ayUserList){
                if (user.getId().equals(id)){
                    return user;
                }
            }
        }
        //step.2  查询数据库中的数据
        AyUserDTO ayUser = ayUserRepository.findById(id).get();
        if(ayUser != null){
            //step.3 将数据插入到Redis缓存中
            redisTemplate.opsForList().leftPush(ALL_USER, ayUser);
        }
        return ayUser;
    }

    @Override
    public List<AyUserDTO> findAll() {
        try{
            System.out.println("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUserDTO> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时：" + (end - start) + "毫秒");
            return ayUserList;
        }catch (Exception e){
            logger.error("method [findAll] error",e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    @Async
    public Future<List<AyUserDTO>> findAsynAll() {
        try{
            System.out.println("开始做任务");
            long start = System.currentTimeMillis();
            List<AyUserDTO> ayUserList = ayUserRepository.findAll();
            long end = System.currentTimeMillis();
            System.out.println("完成任务，耗时：" + (end - start) + "毫秒");
            return new AsyncResult<List<AyUserDTO>>(ayUserList) ;
        }catch (Exception e){
            logger.error("method [findAll] error",e);
            return new AsyncResult<List<AyUserDTO>>(null);
        }
    }

    //@Transactional
    @Override
    public AyUserDTO save(AyUserDTO ayUser) {
        AyUserDTO saveUser =  ayUserRepository.save(ayUser);
        String error = null;
        error.split("/");
        return saveUser;
    }

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void delete(String id) {
        ayUserRepository.deleteById(id);
        logger.info("userId:" + id + "用户被删除");
    }

    @Override
    public Page<AyUserDTO> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUserDTO> findByName(String name){
        return ayUserRepository.findByName(name);
    }

    @Override
    public AyUserDTO findByUserName(String name) {
//        return ayUserDao.findByUserName(name);
        return null; //bingos
    }

    @Override
    public List<AyUserDTO> findByNameLike(String name){
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUserDTO> findByIdIn(Collection<String> ids){
        return ayUserRepository.findByIdIn(ids);
    }


    @Override
//    @Retryable(value= {BusinessException.class},maxAttempts = 5,backoff = @Backoff(delay = 5000,multiplier = 2))
    public boolean update(AyUserDTO ayUser) {
        AyUserDTO user = ayUserRepository.save(ayUser);
        if(user == null){
            //更新失败
//            throw new BusinessException();
        }
        return Boolean.TRUE;
    }
}
