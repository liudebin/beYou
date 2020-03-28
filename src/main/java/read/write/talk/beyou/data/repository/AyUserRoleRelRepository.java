package read.write.talk.beyou.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import read.write.talk.beyou.data.model.AyUserRoleRel;

import java.util.List;

/**
 * 描述：用户角色关联Repository
 * @author Ay
 * @date   2017/10/14.
 */
@Repository
public interface AyUserRoleRelRepository extends JpaRepository<AyUserRoleRel,String> {

    List<AyUserRoleRel> findByUserId( String userID);
}
