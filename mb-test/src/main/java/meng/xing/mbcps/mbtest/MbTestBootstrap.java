package meng.xing.mbcps.mbtest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MbTestBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(MbTestBootstrap.class, args);
	}
}
