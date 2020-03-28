package read.write.talk.beyou.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import read.write.talk.beyou.data.model.AyMood;

/**
 * 描述：说说repository
 * @author Ay
 * @date   2017/12/02
 */
public interface AyMoodRepository extends JpaRepository<AyMood,String> {

}
