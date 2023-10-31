package org.example.microservice_station;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
    @EnableJpaRepositories
    public class microserviceStationApplication {
        public static void main(String[] args) {
            SpringApplication.run(microserviceStationApplication.class, args);
        }
    }
