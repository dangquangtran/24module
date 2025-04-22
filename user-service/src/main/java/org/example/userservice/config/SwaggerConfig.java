package org.example.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(ServerProperties serverProperties) {
        final String securitySchemeName = "bearerAuth";
        Server gatewayServer = new Server()
                .url("/user-service") // <- Đây là prefix khi đi qua API Gateway
                .description("Server qua API Gateway");
        return new OpenAPI()
                .info(new Info()
                        .title("Assignment1 API")
                        .version("1.0")
                        .description("API with JWT"))
                .addServersItem(gatewayServer)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
