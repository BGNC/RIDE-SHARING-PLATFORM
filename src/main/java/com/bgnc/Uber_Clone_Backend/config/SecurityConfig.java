package com.bgnc.Uber_Clone_Backend.config;

import com.bgnc.Uber_Clone_Backend.exception.AuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;



import com.bgnc.Uber_Clone_Backend.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.bgnc.Uber_Clone_Backend.utils.ApiTag.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final AuthenticationProvider authenticationProvider;

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(REGISTER, AUTHENTICATE)
                        .permitAll()  // Register and Authenticate are public
                        .requestMatchers(DRIVER_ROUTE).hasRole("DRIVER")  // Only accessible by DRIVER role
                        .requestMatchers(PASSENGER_ROUTE).hasRole("PASSENGER")  // Only accessible by PASSENGER role
                        .anyRequest()
                        .authenticated())  // All other requests require authentication
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authEntryPoint))  // Custom authentication entry point for error handling
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session management
                .authenticationProvider(authenticationProvider)  // Custom authentication provider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before default filter

        return http.build();
    }
}
