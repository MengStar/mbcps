package meng.xing.user.service;

import meng.xing.user.entity.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface TokenService {

    String getToken(User user);

    Optional<User> getUserByToken(String token);
}
