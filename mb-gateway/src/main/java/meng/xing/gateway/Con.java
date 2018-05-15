package meng.xing.gateway;

import meng.xing.common.User.ResponseUser;
import meng.xing.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Con {
    @Autowired
    UserService userService;

    @GetMapping("/")
    void s() {
        ResponseUser responseUser = userService.findUserByToken("sad");
        if (responseUser == null)
            System.out.println("1111");
    }
}
