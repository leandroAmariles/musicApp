package com.quipux.musicapp.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quipux.musicapp.models.ErrorSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;


@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        ErrorSecurity errorSecurity = ErrorSecurity.builder()
                .timestamp(new Date().toString())
                .error(HttpStatus.UNAUTHORIZED.toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(exchange.getRequest().getPath().value())
                .build();

        return Mono.fromSupplier(() -> {
                    try {
                        return objectMapper.writeValueAsBytes(errorSecurity);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error serializing response", e);
                    }
                })
                .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
                .flatMap(buffer -> exchange.getResponse().writeWith(Mono.just(buffer)));
    }
}
