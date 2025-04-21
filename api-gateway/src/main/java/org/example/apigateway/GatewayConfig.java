package org.example.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
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
                        .filters(f -> f
                                .stripPrefix(1)
                        )
                        .uri("lb://user-service"))
                .route("product-service", r -> r
                        .path("/product-service/**")
                        .filters(f -> f
                                .stripPrefix(1)
                        )
                        .uri("lb://product-service"))
                .build();
    }
}

