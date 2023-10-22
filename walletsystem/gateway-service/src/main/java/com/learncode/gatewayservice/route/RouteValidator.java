package com.learncode.gatewayservice.route;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/welcome",
            "/api/v1/auth/user/signup",
            "/api/v1/auth/user/change-password-with-otp",
            "/api/v1/auth/user/reset-password",
            "/api/v1/auth/user/logout",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
