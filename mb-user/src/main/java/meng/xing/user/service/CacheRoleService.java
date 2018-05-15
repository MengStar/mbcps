package meng.xing.user.service;

import meng.xing.common.User.RoleType;
import meng.xing.user.entity.Role;
import meng.xing.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CacheRoleService implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public CacheRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Cacheable(value = "role", key = "'allRoles'")
    public Set<Role> roles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    @Cacheable(value = "role", key = "'role@'+#roleType.toString()")
    public Role findBy(RoleType roleType) {
        return roleRepository.findByRole(roleType.toString());
    }


}
