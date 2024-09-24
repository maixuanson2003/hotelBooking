package com.example.webHotelBooking.config;

import lombok.experimental.NonFinal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration.jwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @NonFinal
    private static final String signerKey="NQc7mrnHIwVaDA519Ka3ph/ZdHVjvu5NhWkNMfExmAIHpDtO3PShgPqK4w3Rivq7";
    private final String[] swaggerURL={"/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**"};
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(swaggerURL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/checkToken").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                );
        return http.build();

    }
    @Bean
    public JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec=new SecretKeySpec(signerKey.getBytes(),"HmacSHA512");
        return  NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
