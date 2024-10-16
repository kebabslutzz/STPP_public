package com.stpp.movies.services.jwt;

import com.stpp.movies.dto.user.UserLoginDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.entities.User;
import com.stpp.movies.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
    UserRepository userRepository,
    AuthenticationManager authenticationManager,
    PasswordEncoder passwordEncoder
  ) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  //https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
  public User signup(UserRequestDto input) {
    User user = new User()
      .toBuilder()
      .username(input.getUsername())
      .role(input.getRole())
      .email(input.getEmail())
      .password(passwordEncoder.encode(input.getPassword()))
      .status(input.getStatus())
      .build();

    return userRepository.save(user);
  }

  public User authenticate(UserLoginDto input) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        input.getEmail(),
        input.getPassword()
      )
    );

    return userRepository.findByEmail(input.getEmail())
      .orElseThrow();
  }
}
