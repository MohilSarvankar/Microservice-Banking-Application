package com.viper.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.viper.gateway.enums.ResponseStatus;
import com.viper.gateway.model.ApiResponse;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {
	
	private final WebClient webClient;

    public JwtAuthFilter(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		
		// 1. Allow public endpoints (auth-service)
		if(path.contains("/auth/login") || path.contains("/auth/register")) {
			return chain.filter(exchange);
		}
		
		// 2. Extract token
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		
		String token = authHeader.substring(7);		
		
		// 3. Validate token
		return webClient.get()
                .uri("http://auth-service/auth/validate?token=" + token)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMap(response -> {

                    // Our custom response object
                    if (response.getStatus() != ResponseStatus.SUCCESS) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                    // Token valid → forward request
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
	}

	@Override
	public int getOrder() {
		return -1;
	}

}
