package com.example.banktransactionsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Bank Transaction System API")
                .description("An API that can manage Accounts and Transactions.")
                .version("v1.0");
        return new OpenAPI().info(info);
    }
}
