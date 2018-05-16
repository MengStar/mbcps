package meng.xing.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MbChat {
    public static void main(String[] args) {
        SpringApplication.run(MbChat.class);
    }
}
