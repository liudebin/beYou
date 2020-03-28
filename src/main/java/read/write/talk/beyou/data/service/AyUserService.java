package read.write.talk.beyou.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import read.write.talk.beyou.data.model.AyUserDTO;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 描述：用户服务层接口
 * @author 阿毅
 * @date   2017/10/14
 */
public interface AyUserService {

    AyUserDTO findById(String id);

    List<AyUserDTO> findAll();

    Future<List<AyUserDTO>> findAsynAll();

    AyUserDTO save(AyUserDTO AyUserDTO);

    void delete(String id);

    Page<AyUserDTO> findAll(Pageable pageable);

    List<AyUserDTO> findByName(String name);

    AyUserDTO findByUserName(String name);

    List<AyUserDTO> findByNameLike(String name);

    List<AyUserDTO> findByIdIn(Collection<String> ids);

    AyUserDTO findByNameAndPassword(String name, String password);

    AyUserDTO findByNameAndPasswordRetry(String name, String password);

    boolean update(AyUserDTO ayUser);

    Long findUserTotalNum();
}
