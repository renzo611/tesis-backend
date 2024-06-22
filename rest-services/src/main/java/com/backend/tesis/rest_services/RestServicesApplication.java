package com.backend.tesis.rest_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RestServicesApplication {

	@GetMapping("/show-message")
	public ResponseEntity<String> returnMessage() {
		return new ResponseEntity<>("Message response", HttpStatus.OK);
	}

	public static void main(String[] args) {
		SpringApplication.run(RestServicesApplication.class, args);
	}

}
