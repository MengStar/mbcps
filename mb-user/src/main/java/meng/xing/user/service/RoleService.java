package meng.xing.user.service;

import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import org.springframework.stereotype.Service;

import javax.persistence.RollbackException;
import java.util.Set;

@Service
public interface RoleService {

    Set<Role> roles();
    Role findBy(RoleType roleType);
}
