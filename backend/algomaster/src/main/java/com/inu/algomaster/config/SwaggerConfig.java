package com.inu.algomaster.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("querydsl backend Application")
                        .description("쿼리 dsl 연습을 위함")
                        .version("0.0.1-SNAPSHOT"));
    }
}
