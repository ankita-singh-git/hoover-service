package com.hoover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= "com.hoover.*")
public class HooverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HooverServiceApplication.class, args);
	}
}
