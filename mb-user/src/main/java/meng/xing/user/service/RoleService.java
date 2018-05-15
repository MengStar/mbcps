package meng.xing.user.service;

import meng.xing.user.entity.Role;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.Set;

@Service
public interface RoleService {
     @Cacheable(value = "userCache")
     Set<Role> roles();
}
