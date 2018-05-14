package meng.xing.gateway.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密工具类
 * 配置加密算法
 */
@Component
public class PasswordEncoderUtil {
    private static  final BCryptPasswordEncoder passwordEncoder ;
    static  {
       passwordEncoder = new BCryptPasswordEncoder();
    }
    public static String passwordEncoding(String password){
        return passwordEncoder.encode(password);
    }
    public static BCryptPasswordEncoder getPasswordEncoder(){
        return passwordEncoder;
    }
}
