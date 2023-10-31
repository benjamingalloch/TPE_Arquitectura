package org.example.microservice_scooter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("RestClient")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("OpenAPI")
    public OpenAPI customOpenAPI(@Value("${application-description}") String description, @Value("${application-version}") String version) {
        return new OpenAPI()
            .info(new Info().title("microservice_admin")
            .version(version)
            .description(description)
            .license(new License().name("Administration API Licence")));
    }
}
