package com.stpp.movies.services.jwt;

import com.stpp.movies.dto.user.UserLoginDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.entities.User;
import com.stpp.movies.services.user.UserMapper;
import com.stpp.movies.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Transactional
@Service
@Validated
public class AuthenticationService {

  private final JwtService jwtService;

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private static final UserMapper MAPPER = UserMapper.INSTANCE;

  public UserResponseDto register(UserRequestDto input) {
    input.setPassword(passwordEncoder.encode(input.getPassword()));
    return userService.createUser(input);
  }

  public UserResponseDto login(UserLoginDto input) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        input.getEmail(),
        input.getPassword()
      )
    );

    User user = userService.getUserByEmail(input.getEmail());
    String jwtToken = jwtService.generateToken(user);

    UserResponseDto userResponseDto = MAPPER.userToResponseDto(user);

    userResponseDto.setToken(jwtToken);

    return userResponseDto;
  }
}
