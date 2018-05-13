package meng.xing.user;

import meng.xing.user.entity.Role;
import meng.xing.user.entity.RoleType;
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
    public void run(String... args){

        if (roleRepository.count() != 0) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("权限列表：{}", roleRepository.findAll());
            }
            return;
        }
        roleRepository.save(new Role(RoleType.ROLE_DEFAULT.toString()));
        roleRepository.save(new Role(RoleType.ROLE_DEVELOPER.toString()));
        roleRepository.save(new Role(RoleType.ROLE_ADMIN.toString()));
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("权限列表：{}", roleRepository.findAll());
        }
    }
}
