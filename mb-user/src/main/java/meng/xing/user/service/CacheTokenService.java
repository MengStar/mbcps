package meng.xing.user.service;

import meng.xing.user.entity.User;
import meng.xing.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheTokenService implements TokenService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public CacheTokenService(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    @Cacheable(value = "token", key = "'username@'+#user.username")
    public String getToken(User user) {
        return jwtTokenUtil.generateToken(user.getUsername());
    }


    @Override
    public Optional<User> getUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Optional<User> optionalUser = userService.findUser(username);
        if (optionalUser.isPresent() && jwtTokenUtil.validateToken(token, optionalUser.get()))
            return optionalUser;
        return Optional.empty();
    }

    @CacheEvict(value = "token", key = "'username@'+#username")
    public void tokenCacheEvict(String username) {
    }
}
