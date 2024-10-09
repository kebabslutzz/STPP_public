package com.stpp.movies.services.user;

import com.stpp.movies.dto.user.UserEditRequestDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.entities.User;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class UserService {
    private final UserRepository userRepository;

    private static final UserMapper MAPPER = UserMapper.INSTANCE;

    @Transactional
    public Optional<UserResponseDto> getUserById(@Valid Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(MAPPER::userToResponseDto);
    }

    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(MAPPER::userToResponseDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public UserResponseDto createUser(@Valid UserRequestDto userRequestDto) {
        User user = MAPPER.requestDtoToUser(userRequestDto);
        user = userRepository.save(user);
        return MAPPER.userToResponseDto(user);
    }

    @Transactional
    public void deleteUserById(@Valid Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDto editUser(Long userId, UserEditRequestDto userRequestDto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        User user = userRepository.findById(userId).get();
        MAPPER.userEditRequestDtoToUser(userRequestDto, user);
        user = userRepository.save(user);
        return MAPPER.userToResponseDto(user);
    }
}
