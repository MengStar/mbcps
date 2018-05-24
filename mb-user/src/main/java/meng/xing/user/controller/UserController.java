package meng.xing.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import meng.xing.common.User.*;
import meng.xing.user.entity.User;
import meng.xing.user.service.CacheTokenService;
import meng.xing.user.service.CacheUserService;
import meng.xing.user.service.UserService;
import meng.xing.user.util.CacheEvictUtil;
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
public class UserController {

    private final CacheUserService userService;
    private final CacheTokenService tokenService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, CacheTokenService tokenService) {
        this.userService = (CacheUserService) userService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "注册用户", notes = "用户名不能重复。若为子账号，主账号用户名不存在会注册失败。")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestUser", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/register/account")
    public ResponseUser register(@RequestBody @Validated RequestUser requestUser) {

        LOGGER.info("注册用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.addUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.warn("注册用户失败：{}", requestUser);
            return User2ResponseUser.transfer(new User(), "", -1, "注册用户失败");

        }
        User user = optionalUser.get();
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_DEFAULT);
        user = userService.setRoles(user, roles);

        String token = tokenService.getToken(user);
        LOGGER.info("注册用户成功：{},token:{}", user, token);
        CacheEvictUtil.cacheEvict(user, LOGGER, userService, tokenService);
        return User2ResponseUser.transfer(user, token, 1, "注册用户成功");
    }

    @ApiOperation(value = "修改用户", notes = "用户名需存在，且只有password，nickname可以修改，若字段为空则保持原样。")
    @ApiImplicitParams(@ApiImplicitParam(dataType = "RequestNickPass", name = "requestUser", value = "用户信息", required = true))
    @PostMapping("/profile/edit/basic")
    public ResponseUser update(@Validated @RequestBody RequestNickPass requestUser) {

        LOGGER.info("修改用户开始：{}", requestUser);
        Optional<User> optionalUser = userService.updateUser(requestUser);
        if (!optionalUser.isPresent()) {
            LOGGER.warn("修改用户失败：{}", requestUser);
            return User2ResponseUser.transfer(new User(), "", -1, "修改用户失败");
        }
        User user = optionalUser.get();
        LOGGER.info("修改用户成功：{}", user);
        CacheEvictUtil.cacheEvict(user, LOGGER, userService, tokenService);
        return User2ResponseUser.transfer(user, "", 1, "修改用户成功");
    }

    @PostMapping("/login/account")
    public ResponseUser login(@RequestBody @Validated RequestUsernamePassword requestUsernamePassword) {
        Optional<User> optionalUser = userService.findUser(requestUsernamePassword.getUsername());
        if (!optionalUser.isPresent()) {
            LOGGER.warn("用户名不存在{}", requestUsernamePassword);
            return User2ResponseUser.transfer(new User(), "", -1, "用户名不存在");
        }
        User user = optionalUser.get();
        if (user.getPassword().equals(requestUsernamePassword.getPassword())) {
            String token = tokenService.getToken(optionalUser.get());
            LOGGER.info("登陆成功：{}", requestUsernamePassword);
            return User2ResponseUser.transfer(optionalUser.get(), token, 1, "登陆成功");
        } else {
            LOGGER.warn("密码错误：{}", requestUsernamePassword);
            return User2ResponseUser.transfer(new User(), "", -1, "密码错误");
        }
    }

    @GetMapping("/validate/{token}")
    public ResponseUser validate(@PathVariable("token") String token) {
        LOGGER.info("验证token：{}", token);
        Optional<User> optionalUser = tokenService.getUserByToken(token);
        return optionalUser.map(user -> User2ResponseUser.transfer(user, token, 1, "验证成功")).orElseGet(() -> User2ResponseUser.transfer(new User(), "", -1, "验证失败"));
    }

    @ApiOperation(value = "获取子账号信息", notes = "依据主账号的用户名获取子账号信息")
    @GetMapping("/profile/advanced/{mainUsername}")
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
    @GetMapping("/profile/basic/{username}")
    public ResponseUser user(@PathVariable("username") String username) {
        LOGGER.info("获取账号信息开始,username:{}", username);
        Optional<User> user = userService.findUser(username);
        ResponseUser responseUser;
        if (!user.isPresent()) {
            responseUser = User2ResponseUser.transfer(new User(), "", -1, "");
            LOGGER.warn("获取账号信息结束{}", responseUser);
            return responseUser;
        } else {
            responseUser = User2ResponseUser.transfer(user.get(), "", 1, "");
            LOGGER.info("获取账号信息结束{}", responseUser);
            return responseUser;
        }
    }

    @ApiOperation(value = "用户信息(密码)", notes = "根据用户名获取信息")
    @GetMapping("/profile/basicWithPass/{username}")
    public ResponseUser userbyPass(@PathVariable("username") String username) {
        LOGGER.info("获取账号密码信息开始,username:{}", username);
        Optional<User> user = userService.findUser(username);
        ResponseUser responseUser;
        if (!user.isPresent()) {
            responseUser = User2ResponseUser.transfer(new User(), "", -1, "");
            LOGGER.warn("获取账号信息结束{}", responseUser);
            return responseUser;
        } else {
            responseUser = User2ResponseUser.transfer(user.get(), "", 1, "");
            responseUser.setPassword(user.get().getPassword());
            LOGGER.info("获取账号密码信息结束{}", responseUser);
            return responseUser;
        }
    }

}

