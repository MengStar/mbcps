package meng.xing.user;

import meng.xing.common.User.RequestUser;
import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;
import meng.xing.user.repository.RoleRepository;
import meng.xing.user.repository.UserRepository;
import meng.xing.user.service.AccountService;
import meng.xing.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InitFirstDataBase implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(InitFirstDataBase.class);

    private final AccountService accountService;

    @Autowired
    public InitFirstDataBase(RoleRepository roleRepository, UserRepository userRepository, UserService userService, AccountService accountService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.accountService = accountService;

    }

    @Override
    public void run(String... args) {
        //初始化管理员
        Optional<User> optionalUser = userService.addUser(new RequestUser("admin", "admin", "admin", true, ""));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<RoleType> roles = new HashSet<>();
            roles.add(RoleType.ROLE_ADMIN);
            userService.setRoles(user, roles);
        }
        User admin = userService.findUser("admin").get();
        accountService.initAccount(admin.getUsername(), admin.getPassword(), admin.getId());

        if (roleRepository.count() != 0) {
            LOGGER.info("角色列表：{}", roleRepository.findAll());
            return;
        }
        for (RoleType v : RoleType.values()
        ) {
            roleRepository.save(new Role(v.toString()));
        }
        LOGGER.info("角色列表：{}", roleRepository.findAll());

    }


}
