package meng.xing.user.service;

import meng.xing.user.entity.ROLETYPE;
import meng.xing.user.entity.User;

import java.util.Set;

public interface UserService {
    User saveUser(User user);
    User setRoles(Long id,Set<ROLETYPE> roles);


}
