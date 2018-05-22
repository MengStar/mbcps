package meng.xing.gateway.security;


import meng.xing.common.User.ResponseUser;
import meng.xing.common.User.RoleType;
import meng.xing.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * UserDetailsService的实现，根据用户名返回UserDetails
 */
@Service
public class UserDetailsServiceIml implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServiceIml(UserService userService) {
        this.userService = userService;
    }

    public Optional<UserDetails> loadUserByToken(String token) {
        ResponseUser responseUser = userService.findUserByToken(token);
        if (responseUser.isEmpty()) return Optional.empty();
        return Optional.of(new UserDetailsIml(responseUser));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

