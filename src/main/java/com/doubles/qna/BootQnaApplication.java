package com.doubles.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootQnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootQnaApplication.class, args);
	}
}
