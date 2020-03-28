package read.write.talk.beyou.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import read.write.talk.beyou.data.model.AyUserDTO;
import read.write.talk.beyou.data.service.AyUserService;
import read.write.talk.beyou.error.BusinessException;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：用户控制层
 * @author Ay
 * @date   2017/10/28.
 */
@Controller
@RequestMapping("/ayUser")
public class AyUserController {

    @Resource
    private AyUserService ayUserService;

    @RequestMapping("/test")
    public String test(Model model) {
        List<AyUserDTO> ayUser = ayUserService.findAll();
        model.addAttribute("users",ayUser);
        return "ayUser";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<AyUserDTO> ayUser = ayUserService.findAll();
        model.addAttribute("users",ayUser);
        throw new BusinessException("业务异常");
    }

    @RequestMapping("/update")
    public String update(Model model) {
        AyUserDTO ayUser = new AyUserDTO();
        ayUser.setId("1");
        ayUser.setName("阿毅");
        boolean isSuccess = ayUserService.update(ayUser);
        return "success";
    }

    @RequestMapping("/findByNameAndPasswordRetry")
    public String findByNameAndPasswordRetry(Model model) {
        AyUserDTO 阿毅 = ayUserService.findByNameAndPasswordRetry("阿毅", "123456");
        model.addAttribute("users", 阿毅);
        return "success";
    }

}
