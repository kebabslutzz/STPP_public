package com.stpp.movies.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginProcessingUrl("/login").permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout").permitAll());

        return http.build();
    }
}