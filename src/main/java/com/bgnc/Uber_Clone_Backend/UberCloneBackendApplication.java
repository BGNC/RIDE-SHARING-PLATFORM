package com.bgnc.Uber_Clone_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class UberCloneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberCloneBackendApplication.class, args);
	}

}
