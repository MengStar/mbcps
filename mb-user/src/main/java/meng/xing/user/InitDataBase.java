package meng.xing.user;

import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;

import meng.xing.user.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataBase implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(InitDataBase.class);

    @Autowired
    public InitDataBase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

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
