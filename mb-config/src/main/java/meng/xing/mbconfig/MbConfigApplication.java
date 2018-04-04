package meng.xing.mbconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MbConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbConfigApplication.class, args);
	}
}
