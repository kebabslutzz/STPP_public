package com.stpp.movies.services.user;

import com.stpp.movies.dto.user.UserEditRequestDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.dto.user.UserRoleEditRequestDTO;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.entities.Discussion;
import com.stpp.movies.entities.User;
import com.stpp.movies.exceptions.ConflictException;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.CommentRepository;
import com.stpp.movies.repositories.DiscussionRepository;
import com.stpp.movies.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;
  private final DiscussionRepository discussionRepository;
  private final CommentRepository commentRepository;

  public UserResponseDto getUserById(Long id) {
    return userRepository.findById(id)
      .map(MAPPER::userToResponseDto)
      .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository
      .findAll()
      .stream()
      .map(MAPPER::userToResponseDtoWithRoles)
      .toList();
  }

  public UserResponseDto createUser(@Valid UserRequestDto userRequestDto) {
    if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
      throw new ConflictException("User with the email already exists");
    }
    if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
      throw new ConflictException("The username is already taken");
    }

    userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

    User user = MAPPER.requestDtoToUser(userRequestDto);
    user = userRepository.save(user);
    return MAPPER.userToResponseDto(user);
  }

  public void deleteUserById(Long userId) {
    User userDeleted = userRepository.findById(0L)
      .orElseThrow(() -> new NotFoundException("Special user 'user_deleted' not found"));

    // Reassign discussions
    List<Discussion> discussions = discussionRepository.findByUserId(userId);
    for (Discussion discussion : discussions) {
      discussion.setUser(userDeleted);
      discussionRepository.save(discussion);
    }

    // Reassign comments
    List<Comment> comments = commentRepository.findByUserId(userId);
    for (Comment comment : comments) {
      comment.setUser(userDeleted);
      commentRepository.save(comment);
    }

    // Delete the user
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException("User with ID " + userId + " not found");
    }
    userRepository.deleteById(userId);
  }

  public UserResponseDto editUser(Long userId, UserEditRequestDto userRequestDto, User loggedInUser) {
    var user = userRepository.findById(userId)
      .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    if (!loggedInUser.getId().equals(userId)) {
      throw new AccessDeniedException("You are not allowed to edit this user");
    }

    if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
    }
    MAPPER.userEditRequestDtoToUserUser(userRequestDto, user);

    user = userRepository.save(user);
    return MAPPER.userToResponseDto(user);
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
  }

  public UserResponseDto editUserRole(Long userId, UserRoleEditRequestDTO userRoleEditRequestDTO) {
    var user = userRepository.findById(userId)
      .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

    user.setRole(userRoleEditRequestDTO.getRole());
    user = userRepository.save(user);
    return MAPPER.userToResponseDtoWithRoles(user);
  }
}
