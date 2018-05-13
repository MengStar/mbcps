package meng.xing.user.service;

import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.Role;
import meng.xing.user.entity.User;
import meng.xing.user.repository.RoleRepository;
import meng.xing.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceIml(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Optional<User> addUser(User user) {
        User _user = userRepository.findByUsername(user.getUsername());
        return _user != null?  Optional.empty():Optional.of(userRepository.save(user));

    }

    @Override
    public User saveUser(User user) {
        User _user = userRepository.findByUsername(user.getUsername());
        if (_user != null) user.setId(_user.getId());
        return userRepository.save(user);
    }

    @Override
    public User setRoles(Long id, Set<ROLETYPE> roles) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        Set<Role> _roles = new HashSet<>();
        for (ROLETYPE role : roles
                ) {
            _roles.add(roleRepository.findByRole(role.toString()));
        }
        user.setRoles(_roles);
        return userRepository.save(user);
    }
}
