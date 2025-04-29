package org.example.apigateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethod().name();
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (path.startsWith("/user-service/api/v1/user") && method.equals("GET") && !path.contains("/user-service/api/v1/user/")) {
            return chain.filter(exchange);
        }

        if (path.startsWith("/user-service/api/v1/auth/login") && method.equals("POST")) {
            return chain.filter(exchange);
        }
        if (path.startsWith("/user-service/api/v1/user/register") && method.equals("POST")) {
            return chain.filter(exchange);
        }
        if (path.startsWith("/product-service/api/v1/product") && method.equals("GET") && !path.contains("/product-service/api/v1/product/")) {
            return chain.filter(exchange);
        }
        if (path.equals("/product-service/api/v1/product/active") && method.equals("GET")) {
            return chain.filter(exchange);
        }
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")
                || path.startsWith("/user-service/v3/api-docs")|| path.startsWith("/product-service/v3/api-docs")) {
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
//            System.out.println("Claims: " + claims);
            String userId = String.valueOf(claims.get("userId", Long.class)); // userId là String trong JWT
            String role = claims.get("role", String.class);     // role cũng là String
            String username = claims.getSubject();
//            System.out.println("userId: " + userId);
//            System.out.println("role: " + role);
//            System.out.println("username: " + username);
            // Gắn thông tin vào header để các service phía sau sử dụng
            exchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-User-Id", userId)
                            .header("X-Username", username)
                            .header("X-Role", role)
                            .build())
                    .build();

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    // Đảm bảo filter này chạy trước các filter khác
    @Override
    public int getOrder() {
        return -1;
    }
}
