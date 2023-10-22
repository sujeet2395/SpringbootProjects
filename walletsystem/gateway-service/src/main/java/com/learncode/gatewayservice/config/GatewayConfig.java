package com.learncode.gatewayservice.config;

import com.learncode.gatewayservice.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public GatewayFilter customGatewayFilter() {
        return authenticationFilter.apply(new AuthenticationFilter.Config());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(customGatewayFilter()))
                        .uri("http://localhost:8888/"))
                .route("TRANSACTION-SERVICE", r -> r.path("/api/v1/transactions/**")
                        .filters(f -> f.filter(customGatewayFilter()))
                        .uri("http://localhost:8081/"))
                .route("WALLET-SERVICE", r -> r.path("/api/v1/wallets/**")
                        .filters(f -> f.filter(customGatewayFilter()))
                        .uri("http://localhost:8082/"))
                .route("NOTIFICATION-SERVICE", r -> r.path("/api/v1/notifications/**")
                        .filters(f -> f.filter(customGatewayFilter()))
                        .uri("http://localhost:8083/"))
                .build();
    }
}
