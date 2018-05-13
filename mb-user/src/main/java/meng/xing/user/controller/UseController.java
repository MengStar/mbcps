package meng.xing.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.User;
import meng.xing.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
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

    @ApiOperation(value = "注册用户", notes = "用户名不能重复")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestJson", name = "requestJson", value = "用户信息", required = true))
    @PostMapping("/register")
    public UserRetJson register(@RequestBody @Validated RequestJson requestJson) {

        LOGGER.info("注册用户开始：{}", requestJson);
        String username = requestJson.getUsername();
        String password = requestJson.getPassword();
        String nickname = requestJson.getNickname();
        boolean isMain = requestJson.isMain();
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
        return new UserRetJson(user.getUsername(), user.getNickname(), user.isMain(), "", "1", "注册用户成功");
    }

    @ApiOperation(value = "修改用户", notes = "用户名必须已经注册")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestJson", name = "requestJson", value = "用户信息", required = true))
    @PostMapping("/update")
    public UserRetJson update(@RequestBody @Validated RequestJson requestJson) {

        LOGGER.info("修改用户开始：{}", requestJson);
        String username = requestJson.getUsername();
        String password = requestJson.getPassword();
        String nickname = requestJson.getNickname();
        boolean isMain = requestJson.isMain();
        User user = new User(username, password, nickname, isMain);
        Optional<User> optionalUser = userService.updateUser(user);
        if (!optionalUser.isPresent()) {
            LOGGER.info("修改用户失败：{}", user);
            return new UserRetJson(username, nickname, isMain, "", "-1", "修改用户失败");
        }
        user = optionalUser.get();
        LOGGER.info("修改用户成功：{}", user);
        return new UserRetJson(user.getUsername(), user.getNickname(), user.isMain(), "", "1", "修改用户成功");
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRetJson {
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "true为主账号，false为子账号")
    private boolean isMain;
    @ApiModelProperty(value = "登录用的token")
    private String token;
    @ApiModelProperty(value = "1：成功，-1：失败")
    private String code;
    @ApiModelProperty(value = "操作描述")
    private String msg;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class RequestJson {
    @NotEmpty
    @ApiModelProperty(value = "用户姓名")
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "用户密码")
    private String password;
    @NotEmpty
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @NonNull
    @ApiModelProperty(value = "true为主账号，false为子账号")
    private boolean  main;
}