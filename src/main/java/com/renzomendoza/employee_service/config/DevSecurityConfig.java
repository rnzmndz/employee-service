package com.renzomendoza.employee_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("dev")
@EnableWebFluxSecurity
public class DevSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()
                )
//                .addFilterAt(fakeAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

//    @Bean
//    public WebFilter fakeAuthenticationFilter() {
//        return (exchange, chain) -> {
//            var fakeAuth = new UsernamePasswordAuthenticationToken(
//                    "devUser", null,
//                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
//            );
//            return chain.filter(exchange)
//                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(fakeAuth));
//        };
//    }
}