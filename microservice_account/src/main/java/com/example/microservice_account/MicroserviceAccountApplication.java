package com.example.microservice_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


    @SpringBootApplication
    @EnableJpaRepositories
    public class MicroserviceAccountApplication {
        public static void main(String[] args) {
            SpringApplication.run(MicroserviceAccountApplication.class, args);
        }
}
