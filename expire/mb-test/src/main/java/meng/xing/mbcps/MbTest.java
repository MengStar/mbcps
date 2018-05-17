package meng.xing.mbcps;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MbTest {
    public static void main(String[] args) {
        SpringApplication.run(MbTest.class, args);
    }
}
