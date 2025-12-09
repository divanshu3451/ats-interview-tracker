package com.atstrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI atsTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ATS Interview Tracker API")
                        .description("RESTful API for managing job applications and interview tracking")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("ATS Tracker Team")
                                .email("dev@ats-tracker.local")));
    }
}
