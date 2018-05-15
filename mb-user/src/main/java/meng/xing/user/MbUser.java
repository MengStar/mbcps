package meng.xing.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@EnableCaching
public class MbUser {
    public static void main(String[] args) {
        SpringApplication.run(MbUser.class, args);
    }
}
