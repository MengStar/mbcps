package meng.xing.gateway.service;

import meng.xing.common.User.ResponseUser;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public ResponseUser findUserByToken(String token) {
        return new ResponseUser();
    }
}