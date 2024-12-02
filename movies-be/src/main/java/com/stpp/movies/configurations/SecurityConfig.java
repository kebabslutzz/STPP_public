package com.stpp.movies.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  //  private static final String ALLOWED_ORIGIN = System.getProperty("ALLOWED_ORIGIN", "http://localhost:3000");
  //    private static final List<String> ALLOWED_ORIGIN = List.of("http://localhost:3000", "http://react-stpp-movies.s3-website.eu-north-1.amazonaws.com");
  private static final String ALLOWED_ORIGIN = System.getProperty("ALLOWED_ORIGIN", "http://react-stpp-movies.s3-website.eu-north-1.amazonaws.com");
  private static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PATCH", "DELETE", "PUT");

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeRequests(authorizeRequests -> authorizeRequests
        // Public endpoints (read operations)
        .requestMatchers(HttpMethod.GET, "/api/v1/movies/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/movies/*/discussions").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/movies/*/discussions/*/comments").permitAll()

        // Authentication required endpoints
        // Movie creation and editing
        .requestMatchers(HttpMethod.POST, "/api/v1/movies").authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/movies/**").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/movies/**").authenticated()

        // Discussion creation, editing, deletion
        .requestMatchers(HttpMethod.POST, "/api/v1/movies/*/discussions").authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/movies/*/discussions/*").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/movies/*/discussions/*").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/movies/*/discussions/*").authenticated()

        // Comment creation, editing, deletion
        .requestMatchers(HttpMethod.POST, "/api/v1/movies/*/discussions/*/comments").authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/movies/*/discussions/*/comments/*").authenticated()
        .requestMatchers(HttpMethod.PATCH, "/api/v1/movies/*/discussions/*/comments/*").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/movies/*/discussions/*/comments/*").authenticated()

        // Movie poster ger
        .requestMatchers(HttpMethod.GET, "/api/v1/files/**").permitAll()

        // Public authentication endpoints
        .requestMatchers(HttpMethod.POST, "/api/v1/users/login", "/api/v1/users/signup").permitAll()
        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

        // Any other request requires authentication
        .anyRequest().authenticated()
      )
      .sessionManagement(sessionManagement -> sessionManagement
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
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
//    configuration.setAllowedOrigins(ALLOWED_ORIGIN);
    configuration.setAllowedOrigins(List.of(ALLOWED_ORIGIN));
    configureCommonCORS(configuration);
    return buildCorsConfigurationSource(configuration);
  }

  private void configureCommonCORS(CorsConfiguration configuration) {
    configuration.setAllowedMethods(ALLOWED_METHODS);
    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    configuration.setExposedHeaders(List.of("Authorization"));
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
    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
  }
}
