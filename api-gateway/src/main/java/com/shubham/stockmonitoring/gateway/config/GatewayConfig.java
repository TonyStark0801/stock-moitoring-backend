package com.shubham.stockmonitoring.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service Routes
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("http://localhost:8081"))
                
                // Profile Service Routes
                .route("profile-service", r -> r
                        .path("/api/profile/**")
                        .filters(f -> f.addRequestHeader("X-Service", "profile"))
                        .uri("http://localhost:8082"))
                
                // Master Data Service Routes
                .route("master-data-service", r -> r
                        .path("/api/stocks/**", "/api/exchanges/**", "/api/sectors/**")
                        .filters(f -> f.addRequestHeader("X-Service", "master-data"))
                        .uri("http://localhost:8083"))
                
                .build();
    }
}
