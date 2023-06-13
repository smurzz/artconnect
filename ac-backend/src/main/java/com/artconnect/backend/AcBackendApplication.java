package com.artconnect.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@EnableScheduling
public class AcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcBackendApplication.class, args);
	}

}
