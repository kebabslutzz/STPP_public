package com.stpp.movies.configurations;

import com.stpp.movies.services.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final HandlerExceptionResolver handlerExceptionResolver;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(
    JwtService jwtService,
    UserDetailsService userDetailsService,
    HandlerExceptionResolver handlerExceptionResolver
  ) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      filterChain.doFilter(request, response);
//      logger.error("No JWT token found in request headers ya CUNT");
      return;
    }
//    logger.error("Good job on adding a Token, dumbass!");

    try {
//      logger.error("Trying the catch block, wish me luck");
      final String jwt = authHeader.substring(7);
//      String str = String.format("Jwt stringus: %s", jwt);
//      logger.error(str);
      final String userEmail = jwtService.extractUsername(jwt);
//      str = String.format("User emailus: %s", userEmail);
//      logger.error(str);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (userEmail != null && authentication == null) {
        logger.error("It seems like email is not null and it is authenticated, huh? Who would have fought?");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        String str2 = String.format("User namus is :", userDetails.getUsername());
        logger.error(str2);

        if (jwtService.isTokenValid(jwt, userDetails)) {
          logger.error("Tokenus is validus! GZ");
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
//        else {
//          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//          return;
//        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
//      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
}
