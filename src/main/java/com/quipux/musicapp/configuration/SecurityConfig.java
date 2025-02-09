package com.quipux.musicapp.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    private static final String[] AUTH_WHITELIST = {
            "/music/unsecured/**",
            "/h2-console/**",
    };


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .csrf().disable()
                .build();
    }

}
