package org.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://user-service"))  // Load balanced service name from Eureka
                .route("product-service", r -> r
                        .path("/product-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://product-service"))  // Load balanced service name from Eureka
                .build();
    }
}
