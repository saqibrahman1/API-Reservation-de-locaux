package com.example.reserve_local

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfiguration {

   @Bean
    fun apiInfo(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API du service de gestion des reservation")
                    .description("API du service de gestion des reservation")
                    .version("1.0.0")
            )
    }
}