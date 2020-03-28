package read.write.talk.beyou.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.data.model.AyUserRoleRel;
import read.write.talk.beyou.data.service.AyRoleService;
import read.write.talk.beyou.data.service.AyUserRoleRelService;
import read.write.talk.beyou.data.service.AyUserService;
import read.write.talk.beyou.error.BusinessException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：自定义用户服务类
 * @author Ay
 * @date   2017/12/10.
 */
//@Service
public class CustomUserService implements UserDetailsService{

    @Resource
    private AyUserService ayUserService;

    @Resource
    private AyUserRoleRelService ayUserRoleRelService;

    @Resource
    private AyRoleService ayRoleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AyUserDTO ayUser = ayUserService.findByUserName(name);
        if(ayUser == null){
            throw new BusinessException("用户不存在");
        }
        //获取用户所有的关联角色
        List<AyUserRoleRel> ayRoleList = ayUserRoleRelService.findByUserId(ayUser.getId());
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        if(ayRoleList != null && ayRoleList.size() > 0){
            for(AyUserRoleRel rel:ayRoleList){
                //获取用户关联角色名称
                String roleName = ayRoleService.find(rel.getRoleId()).getName();
                authorityList.add(new SimpleGrantedAuthority(roleName));
            }
        }
        return new User(ayUser.getName(), ayUser.getPassword(), authorityList);
    }
}


