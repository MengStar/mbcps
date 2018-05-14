package meng.xing.user.service;

import meng.xing.user.entity.Role;
import meng.xing.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceIml implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceIml(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> roles() {
        return new HashSet<>(roleRepository.findAll());
    }
}
