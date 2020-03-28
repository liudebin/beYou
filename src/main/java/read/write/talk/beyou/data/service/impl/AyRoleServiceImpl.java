package read.write.talk.beyou.data.service.impl;

import org.springframework.stereotype.Service;
import read.write.talk.beyou.data.model.AyRole;
import read.write.talk.beyou.data.repository.AyRoleRepository;
import read.write.talk.beyou.data.service.AyRoleService;

import javax.annotation.Resource;

/**
 * 描述：用户角色Service
 * @author Ay
 * @date   2017/12/2
 */
@Service
public class AyRoleServiceImpl implements AyRoleService {

    @Resource
    private AyRoleRepository ayRoleRepository;

    @Override
    public AyRole find(String id){
        return ayRoleRepository.findById(id).get();
    }
}
