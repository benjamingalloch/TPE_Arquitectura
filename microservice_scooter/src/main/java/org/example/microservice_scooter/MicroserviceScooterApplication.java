package org.example.microservice_scooter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MicroserviceScooterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceScooterApplication.class, args);
    }
}
