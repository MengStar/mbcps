package meng.xing.user.service;

import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> addUser(User user) throws Exception;
    Optional<User> updateUser(User user);
    User setRoles(Long id,Set<ROLETYPE> roles);


}
