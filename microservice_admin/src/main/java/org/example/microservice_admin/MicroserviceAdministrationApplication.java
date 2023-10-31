package org.example.microservice_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MicroserviceAdministrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAdministrationApplication.class, args);
    }
}
