package nl.ckarakoc.eshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("JWT Bearer token");

        SecurityScheme apiKeyScheme = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .in(SecurityScheme.In.HEADER)
            .name("X-API-KEY")
            .scheme("api-token")
            .description("API token");

        SecurityRequirement bearerRequirement = new SecurityRequirement()
            .addList("Bearer Authentication");
        SecurityRequirement apiKeyRequirement = new SecurityRequirement()
            .addList("API Key");

        return new OpenAPI()
            .info(new Info()
                .title("E-Shop API")
                .version("1.0.0")
                .description("E-Shop REST API Documentation")
                .contact(new Contact()
                    .name("API Support")
                    .email("support@eshop.com")))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication", bearerScheme)
                .addSecuritySchemes("API Key", apiKeyScheme))
            .addSecurityItem(bearerRequirement)
            .addSecurityItem(apiKeyRequirement);
    }
}
