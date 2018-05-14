package meng.xing.user.service;

import meng.xing.user.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {
     Set<Role> roles();
}
