package meng.xing.user.controller;

import io.swagger.annotations.ApiOperation;
import meng.xing.common.User.ResponseUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;
import meng.xing.user.service.RoleService;
import meng.xing.user.service.UserService;
import meng.xing.user.util.User2ResponseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @ApiOperation(value = "角色集合", notes = "返回全部角色集合")
    @GetMapping("/")
    public Set<Role> roles() {
        Set<Role> roles = roleService.roles();
        LOGGER.info("获取角色集合：{}", roles);
        return roles;
    }

    @PostMapping("/{username}")
    @ApiOperation(value = "设置用户角色", notes = "以角色集合中的角色为准")
    public ResponseUser setRoles(@PathVariable("username") String username, @RequestBody Set<RoleType> roles) {
        Optional<User> optionalUser = userService.findUser(username);
        LOGGER.info("设置用户username：{}的角色列表：{}", username, roles);
        if (!optionalUser.isPresent())
            return new ResponseUser("", -1, "用户不存在");
        User user = userService.setRoles(optionalUser.get(), roles);
        return User2ResponseUser.transfer(user, "", 1, "设置权限成功");

    }


}
