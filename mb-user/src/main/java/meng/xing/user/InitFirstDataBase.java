package meng.xing.user;

import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;

import meng.xing.user.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class InitFirstDataBase implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(InitFirstDataBase.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InitFirstDataBase(RoleRepository roleRepository, @Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {

        String sql = "SELECT count(*) FROM sys_user";
        long count = jdbcTemplate.queryForObject(sql, Long.class);
        LOGGER.info("已注册用户数（包括子账号）：{}", count);

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
