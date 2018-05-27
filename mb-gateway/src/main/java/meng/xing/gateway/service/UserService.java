package meng.xing.gateway.service;

import meng.xing.common.User.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "mb-user", fallback = UserServiceFallback.class )
public interface UserService {
    @RequestMapping(method = RequestMethod.GET, value = "/validate/{token}")
    ResponseUser findUserByToken(@PathVariable("token") String token);
}

