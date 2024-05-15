package com.example.assetmanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Asset Management System API")
                .description("An API that can manage Assets and Users.")
                .version("v1.0");
        return new OpenAPI().info(info);
    }
}
