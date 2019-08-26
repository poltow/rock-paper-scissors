package com.ciklum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class GameApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GameApplication.class, args);
	}

}
