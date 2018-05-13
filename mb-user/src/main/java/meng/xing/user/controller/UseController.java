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
public class UseController {

    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(UseController.class);

    @Autowired
    public UseController(UserService userService) {
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
}

