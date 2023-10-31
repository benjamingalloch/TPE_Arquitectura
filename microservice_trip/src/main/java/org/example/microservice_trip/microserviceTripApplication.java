package org.example.microservice_trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class microserviceTripApplication {
    public static void main(String[] args) {
        SpringApplication.run(microserviceTripApplication.class, args);
    }
}

