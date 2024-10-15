package com.stpp.movies.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String ALLOWED_ORIGIN = System.getProperty("ALLOWED_ORIGIN", "http://localhost:3000");
  private static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PATCH", "DELETE", "PUT");

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeRequests(authorizeRequests -> authorizeRequests
        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        .anyRequest().authenticated())
      .exceptionHandling(exceptionHandling ->
        exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint()))
      .formLogin(formLogin -> formLogin
        .loginProcessingUrl("/login").permitAll())
      .logout(logout -> logout
        .logoutUrl("/logout").permitAll());

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGIN));
    configureCommonCORS(configuration);
    return buildCorsConfigurationSource(configuration);
  }

  private void configureCommonCORS(CorsConfiguration configuration) {
    configuration.setAllowedMethods(ALLOWED_METHODS);
    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    configuration.setAllowCredentials(true);
  }

  private CorsConfigurationSource buildCorsConfigurationSource(CorsConfiguration configuration) {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/v1/**", configuration);
    source.registerCorsConfiguration("/v3/api-docs/**", configuration);
    source.registerCorsConfiguration("/swagger-ui/**", configuration);
    source.registerCorsConfiguration("/swagger-ui.html", configuration);
    return source;
  }

  @Bean
  public AuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.NOT_FOUND);
  }
}
