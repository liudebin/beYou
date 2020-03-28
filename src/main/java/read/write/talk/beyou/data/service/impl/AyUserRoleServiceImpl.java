package read.write.talk.beyou.data.service.impl;

import org.springframework.stereotype.Service;
import read.write.talk.beyou.data.model.AyUserRoleRel;
import read.write.talk.beyou.data.repository.AyUserRoleRelRepository;
import read.write.talk.beyou.data.service.AyUserRoleRelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：用户角色关联Service
 * @author   Ay
 * @date     2017/12/10.
 */
@Service
public class AyUserRoleServiceImpl implements AyUserRoleRelService {

    @Resource
    private AyUserRoleRelRepository ayUserRoleRelRepository;

    @Override
    public List<AyUserRoleRel> findByUserId(String userId) {
        return ayUserRoleRelRepository.findByUserId(userId);
    }
}
