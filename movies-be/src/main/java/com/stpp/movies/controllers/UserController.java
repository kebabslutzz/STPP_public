package com.stpp.movies.controllers;

import com.stpp.movies.dto.user.UserEditRequestDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  @Operation(summary = "Get a user by ID", description = "Fetches a user from the database based on the user ID.", responses = {
    @ApiResponse(responseCode = "200", description = "User found and returned successfully"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping("/{id}")
  public UserResponseDto getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @Operation(summary = "Get all users", description = "Fetches all users from the database.", responses = {
    @ApiResponse(responseCode = "200", description = "List of users returned successfully")
  })
  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @Operation(summary = "Create a new user", description = "Create and return a new user", responses = {
    @ApiResponse(responseCode = "201", description = "User created successfully and returned"),
    @ApiResponse(responseCode = "400", description = "User creation failed due to invalid request body")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
    UserResponseDto user = userService.createUser(userRequestDto);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(user.getId())
      .toUri();
    return ResponseEntity.created(location).body(user);
  }

  @Operation(summary = "Delete a user by ID", description = "Deletes a user from database based on the user ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "User found and deleted successfully"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
  }

  @Operation(summary = "Edit a user by ID", description = "Edits a user in the database based on the user ID.", responses = {
    @ApiResponse(responseCode = "200", description = "User found and edited successfully"),
    @ApiResponse(responseCode = "400", description = "User edit failed due to invalid request body"),
    @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PutMapping("/{userId}")
  public UserResponseDto editUser(@Valid @PathVariable Long userId, @Valid @RequestBody UserEditRequestDto userRequestDto) {
    return userService.editUser(userId, userRequestDto);
  }
}
