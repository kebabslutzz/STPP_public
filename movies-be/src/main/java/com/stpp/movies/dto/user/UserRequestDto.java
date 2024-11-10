package com.stpp.movies.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserRequestDto {
  @NotBlank(message = "Username should not be blank")
  @NotEmpty(message = "Username should not be empty")
  @Size(min = 1, max = 256, message = "Username must be between {min} and {max} characters")
  private String username;

  @NotBlank(message = "Password should not be blank")
  @NotEmpty(message = "Password should not be empty")
  @Size(min = 8, max = 64, message = "Password must be between {min} and {max} characters")
  private String password;

  @Email
  @NotEmpty(message = "Email should not be empty")
  @NotBlank(message = "Email should not be blank")
  private String email;
}
