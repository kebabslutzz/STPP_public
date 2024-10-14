package com.stpp.movies.dto.user;

import com.stpp.movies.enumerators.Role;
import com.stpp.movies.enumerators.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserResponseDto {
  private Long id;
  private String username;
  private String password;
  private String email;
  private Role role;
  private Status status;
  private OffsetDateTime dateCreated;
  private OffsetDateTime dateModified;
}
