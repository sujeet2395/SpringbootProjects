package com.learncode.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learncode.gatewayservice.payload.ConnValidatedResponse;
import com.learncode.gatewayservice.payload.ErrorResponse;
import com.learncode.gatewayservice.route.RouteValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Value("${application.authorization.token.prefix}")
	private String prefix;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RouteValidator validator;

	@Autowired
	private WebClient webClient;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				List<String> authHeader;
				try {
					// header contains authorization header or not
					if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
						throw new RuntimeException("Missing authorization header.");
					}
					authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
					// header contains valid authorization header or not
					if (Objects.isNull(authHeader) || authHeader.isEmpty() || !authHeader.get(0).startsWith(prefix + " ")) {
						throw new RuntimeException("Invalid authorization header.");
					}
				} catch (Exception e) {
					return getVoidMono(exchange, e);
				}
				return webClient.method(HttpMethod.GET).uri("http://AUTH-SERVICE/api/v1/auth/user/validate-token")
						.header(HttpHeaders.AUTHORIZATION, authHeader.get(0)).retrieve()
						.bodyToMono(ConnValidatedResponse.class)
						.map(response -> {
							log.info("Response: "+response);
							return exchange;
						}).flatMap(chain::filter)
						.onErrorResume(error -> getVoidMono(exchange, error));
			}
			log.info("Navigated to public url.");
			return chain.filter(exchange);
		};
	}

	private Mono<Void> getVoidMono(ServerWebExchange exchange, Throwable e) {
		ServerHttpResponse response = exchange.getResponse();
		ErrorResponse errorResponse = getErrorResponse(exchange, response, e);
		DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
		try {
			return response.writeWith(Mono.just(objectMapper.writeValueAsBytes(errorResponse)).map(dataBufferFactory::wrap));
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}
		return response.setComplete();
	}

	private ErrorResponse getErrorResponse(ServerWebExchange exchange, ServerHttpResponse response, Throwable error) {
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		String path = exchange.getRequest().getPath().value();
		if(error instanceof WebClientRequestException) {
			WebClientRequestException webClientException = (WebClientRequestException) error;
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			log.error("WebClientRequest: "+webClientException.getMessage());
			return ErrorResponse.builder()
					.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.error(webClientException.getMessage())
					.message(webClientException.getMessage())
					.path(path)
					.build();
		}
		if (error instanceof WebClientResponseException) {
			WebClientResponseException webClientException = (WebClientResponseException) error;
			response.setStatusCode(webClientException.getStatusCode());
			log.error("WebClientResponse: "+webClientException.getMessage());
			return ErrorResponse.builder()
					.httpStatus(webClientException.getStatusCode().value())
					.error(webClientException.getStatusText())
					.message("You are not authenticated.")
					.path(path)
					.build();
		}
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		log.error("Exception: "+error.getMessage());
		return ErrorResponse.builder()
				.httpStatus(HttpStatus.UNAUTHORIZED.value())
				.error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.message(error.getMessage())
				.path(path)
				.build();
	}

	public static class Config {

	}
}