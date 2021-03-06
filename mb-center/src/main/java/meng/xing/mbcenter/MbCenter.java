package meng.xing.mbcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MbCenter {

    public static void main(String[] args) {
        SpringApplication.run(MbCenter.class, args);
    }
}
