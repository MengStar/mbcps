package meng.xing.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import meng.xing.common.User.RequestUandP;
import meng.xing.common.User.RequestUser;
import meng.xing.common.User.ResponseUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.User;
import meng.xing.user.service.UserService;
import meng.xing.user.util.User2ResponseUser;
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

    @ApiOperation(value = "注册用户", notes = "用户名不能重复。若为子账号，主账号用户名不存在会注册失败。")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestUser", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/register")
    public ResponseUser register(@RequestBody @Validated RequestUser requestUser) {

        LOGGER.info("注册用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.addUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.warn("注册用户失败：{}", requestUser);
            return new ResponseUser(requestUser, -1, "注册用户失败");
        }
        User user = optionalUser.get();
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_DEFAULT);
        user = userService.setRoles(user, roles);

        String token = userService.getToken(user, user.getPassword());
        LOGGER.info("注册用户成功：{},token:{}", user, token);
        return User2ResponseUser.transfer(user, token, 1, "注册用户成功");
    }

    @ApiOperation(value = "修改用户", notes = "用户名需存在，且只有password，nickname可以修改，若字段为空则保持原样。")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestUser", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/update")
    public ResponseUser update(@RequestBody RequestUser requestUser) {

        LOGGER.info("修改用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.updateUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.warn("修改用户失败：{}", requestUser);
            return new ResponseUser(requestUser, -1, "修改用户失败");
        }
        User user = optionalUser.get();
        LOGGER.info("修改用户成功：{}", user);
        return User2ResponseUser.transfer(user, "", 1, "修改用户成功");
    }

    @PostMapping("/login")
    public ResponseUser login(@RequestBody @Validated RequestUandP requestUandP) {
        Optional<User> optionalUser = userService.findUser(requestUandP.getUsername());
        if (!optionalUser.isPresent()) {
            LOGGER.warn("用户名不存在{}", requestUandP);
            return User2ResponseUser.transfer(new User(), "", -1, "用户名不存在");
        }
        String token = userService.getToken(optionalUser.get(), requestUandP.getPassword());
        if (token == null) {
            LOGGER.warn("密码错误：{}", requestUandP);
            return User2ResponseUser.transfer(new User(), "", -1, "密码错误");
        }
        LOGGER.info("登陆成功：{}", requestUandP);
        return User2ResponseUser.transfer(optionalUser.get(), token, 1, "登陆成功");
    }

    @GetMapping("/validate/{token}")
    public ResponseUser validate(@PathVariable("token") String token) {
        LOGGER.info("验证token：{}", token);
        Optional<User> optionalUser = userService.getUserByToken(token);
        return optionalUser.map(user -> User2ResponseUser.transfer(user, token, 1, "验证成功")).orElseGet(() -> User2ResponseUser.transfer(new User(), "", -1, "验证失败"));
    }

    @ApiOperation(value = "获取子账号信息", notes = "依据主账号的用户名获取子账号信息")
    @GetMapping("/{mainUsername}/subUsers")
    public Set<ResponseUser> subUsers(@PathVariable("mainUsername") String mainUsername) {
        LOGGER.info("获取子账号信息开始,mainUsername:{}", mainUsername);
        Set<User> subUsers = userService.findSubUsers(mainUsername);
        Set<ResponseUser> ret = new HashSet<>();

        for (User subUser : subUsers
                ) {
            ret.add(User2ResponseUser.transfer(subUser, "", 1, ""));
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
            responseUser = User2ResponseUser.transfer(new User(), "", -1, "");
            LOGGER.info("获取账号信息结束{}", responseUser);
            return responseUser;
        } else {
            responseUser = User2ResponseUser.transfer(user.get(), "", 1, "");
            LOGGER.info("获取账号信息结束{}", responseUser);
            return responseUser;
        }
    }

}

