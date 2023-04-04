package cms.tuitionclass.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CmsTuitionClassServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsTuitionClassServiceApplication.class, args);
	}

}
