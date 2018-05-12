package meng.xing.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;
import meng.xing.user.repository.RoleRepository;
import meng.xing.user.repository.UserRepository;
import meng.xing.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UseController {

    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UseController.class);

    @Autowired
    public UseController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserRetJson register(@RequestBody Map<String, Object> map) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("注册用户开始：{}", map);
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String nickname = map.get("nickname").toString();
        String isMain = map.get("isMain").toString();
        User user = new User(username, password, nickname, isMain);
        user = userService.saveUser(user);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("注册用户保存基本信息：{}", user);
        Set<ROLETYPE> roles = new HashSet<>();
        roles.add(ROLETYPE.ROLE_DEFAULT);
        user = userService.setRoles(user.getId(), roles);
        if (LOGGER.isInfoEnabled())
            LOGGER.info("注册用户设置基本权限：{}", user);
        return new UserRetJson(user.getUsername(), user.getNickname(), user.getIsMain(), "1", "1", "");
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRetJson {
    private String username;
    private String nickname;
    private String isMain;
    private String token;
    private String code;
    private String msg;
}
