package meng.xing.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.User;
import meng.xing.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
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
    public UserRetJson register(@RequestBody Map<String, Object> map) throws Exception {

        LOGGER.info("注册用户开始：{}", map);
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String nickname = map.get("nickname").toString();
        String isMain = map.get("isMain").toString();
        User user = new User(username, password, nickname, isMain);
        Optional<User> optionalUser = userService.addUser(user);
        if (!optionalUser.isPresent()) {

            LOGGER.info("注册用户失败：{}", user);
            return new UserRetJson(username, nickname, isMain, "", "-1", "注册用户失败");
        }
        user = optionalUser.get();

        LOGGER.info("注册用户保存基本信息：{}", user);
        Set<ROLETYPE> roles = new HashSet<>();
        roles.add(ROLETYPE.ROLE_DEFAULT);
        user = userService.setRoles(user.getId(), roles);
        LOGGER.info("注册用户设置基本权限：{}", user);
        return new UserRetJson(user.getUsername(), user.getNickname(), user.getIsMain(), "", "1", "注册用户成功");
    }

    @PostMapping("/update")
    public UserRetJson update(@RequestBody Map<String, Object> map) throws Exception {

        LOGGER.info("修改用户开始：{}", map);
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String nickname = map.get("nickname").toString();
        String isMain = map.get("isMain").toString();
        User user = new User(username, password, nickname, isMain);
        Optional<User> optionalUser = userService.updateUser(user);
        if (!optionalUser.isPresent()) {
            LOGGER.info("修改用户失败：{}", user);
            return new UserRetJson(username, nickname, isMain, "", "-1", "修改用户失败");
        }
        user = optionalUser.get();
        LOGGER.info("修改用户成功：{}", user);
        return new UserRetJson(user.getUsername(), user.getNickname(), user.getIsMain(), "", "1", "修改用户成功");
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
