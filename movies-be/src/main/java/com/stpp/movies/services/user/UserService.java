package com.stpp.movies.services.user;

import com.stpp.movies.dto.user.UserEditRequestDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.entities.User;
import com.stpp.movies.exceptions.ConflictException;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Validated
public class UserService {
  private final UserRepository userRepository;

  private static final UserMapper MAPPER = UserMapper.INSTANCE;

  public UserResponseDto getUserById(Long id) {
    return userRepository.findById(id)
      .map(MAPPER::userToResponseDto)
      .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository
      .findAll()
      .stream()
      .map(MAPPER::userToResponseDto)
      .toList();
  }

  public UserResponseDto createUser(@Valid UserRequestDto userRequestDto) {
    if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
      throw new ConflictException("User with email " + userRequestDto.getEmail() + " already exists");
    }
    if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
      throw new ConflictException("User with username " + userRequestDto.getUsername() + " already exists");
    }

    User user = MAPPER.requestDtoToUser(userRequestDto);
    user = userRepository.save(user);
    return MAPPER.userToResponseDto(user);
  }

  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException("User with ID " + id + " not found");
    }
    userRepository.deleteById(id);
  }

  public UserResponseDto editUser(Long userId, UserEditRequestDto userRequestDto) {
    var user = userRepository.findById(userId)
      .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    MAPPER.userEditRequestDtoToUser(userRequestDto, user);
    user = userRepository.save(user);
    return MAPPER.userToResponseDto(user);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
  }
}
