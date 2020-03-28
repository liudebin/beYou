package read.write.talk.beyou.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import read.write.talk.beyou.data.model.AyRole;

/**
 * 描述：用户角色Repository
 * @author Ay
 * @date   2017/12/10.
 */
@Repository
public interface AyRoleRepository extends JpaRepository<AyRole,String> {

}
