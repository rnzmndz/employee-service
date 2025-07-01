package com.renzomendoza.employee_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Employee Service API")
                        .description("API for managing employee information")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@employee-service.com")));
    }
}