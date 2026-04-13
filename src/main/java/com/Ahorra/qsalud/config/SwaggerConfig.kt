package com.Ahorra.qsalud.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("API QSalud - Ahorra")
                .version("1.0.0")
                .description("Servicios de integración para Quálitas Salud: Cotización, Emisión, Firmas y Pagos."))
           
            .addSecurityItem(SecurityRequirement().addList("BearerAuth"))
            .addSecurityItem(SecurityRequirement().addList("SiseUser"))
            .addSecurityItem(SecurityRequirement().addList("SisePass"))
            .components(Components()
                // Esquema para Token Bearer (JWT)
                .addSecuritySchemes("BearerAuth", SecurityScheme()
                    .name("Authorization")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
                // Esquemas para Headers de Quálitas
                .addSecuritySchemes("SiseUser", SecurityScheme()
                    .name("X-Sise-User")
                    .type(SecurityScheme.Type.APIKEY)
                    .`in`(SecurityScheme.In.HEADER))
                .addSecuritySchemes("SisePass", SecurityScheme()
                    .name("X-Sise-Pass")
                    .type(SecurityScheme.Type.APIKEY)
                    .`in`(SecurityScheme.In.HEADER))
            )
    }
}