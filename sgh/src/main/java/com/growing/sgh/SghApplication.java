package com.growing.sgh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SghApplication {

	public static void main(String[] args) {
		SpringApplication.run(SghApplication.class, args);
	}

}
