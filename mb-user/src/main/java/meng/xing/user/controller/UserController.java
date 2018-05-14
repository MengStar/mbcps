package meng.xing.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import meng.xing.user.controller.Meta.RequestUser;
import meng.xing.user.controller.Meta.ResponseUser;
import meng.xing.user.entity.RoleType;
import meng.xing.user.entity.User;
import meng.xing.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "注册用户", notes = "用户名不能重复,字段不能为空")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestUser", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/register")
    public ResponseUser register(@RequestBody @Validated RequestUser requestUser) {

        LOGGER.info("注册用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.addUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.info("注册用户失败：{}", requestUser);
            return new ResponseUser(requestUser, -1, "注册用户失败");
        }
        User user = optionalUser.get();
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_DEFAULT);
        user = userService.setRoles(user.getId(), roles);
        LOGGER.info("注册用户成功：{}", user);
        return new ResponseUser(user, "", 1, "注册用户成功");
    }

    @ApiOperation(value = "修改用户", notes = "用户名必须已经注册,空字段不修改")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestUser", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/update")
    public ResponseUser update(@RequestBody RequestUser requestUser) {

        LOGGER.info("修改用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.updateUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.info("修改用户失败：{}", requestUser);
            return new ResponseUser(requestUser, -1, "修改用户失败");
        }
        User user = optionalUser.get();
        LOGGER.info("修改用户成功：{}", user);
        return new ResponseUser(user, "", 1, "修改用户成功");
    }

    @ApiOperation(value = "获取子账号信息", notes = "依据主账号的用户名获取子账号信息")
    @GetMapping("/{mainUsername}/subUsers")
    public Set<ResponseUser> subUsers(@PathVariable("mainUsername") String mainUsername) {
        LOGGER.info("获取子账号信息开始,mainUsername:{}", mainUsername);
        Set<User> subUsers = userService.findSubUsers(mainUsername);
        Set<ResponseUser> ret = new HashSet<>();

        for (User subUser : subUsers
                ) {
            ret.add(new ResponseUser(subUser, "", 1, ""));
        }
        LOGGER.info("获取子账号信息结束{}", ret);
        return ret;
    }

    @ApiOperation(value = "用户信息", notes = "根据用户名获取信息")
    @GetMapping("/{username}")
    public ResponseUser user(@PathVariable("username") String username) {
        LOGGER.info("获取账号信息开始,username:{}", username);
        Optional<User> user = userService.findUser(username);
        ResponseUser responseUser;
        if (!user.isPresent()) {
            responseUser = new ResponseUser(new User(), "", -1, "");
            LOGGER.info("获取账号信息结束{}", responseUser);
            return responseUser;
        } else {
            responseUser = new ResponseUser(user.get(), "", 1, "");
            LOGGER.info("获取账号信息结束{}", responseUser);
            return responseUser;
        }
    }

}

