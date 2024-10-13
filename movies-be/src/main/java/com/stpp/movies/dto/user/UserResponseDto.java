package com.stpp.movies.dto.user;

import com.stpp.movies.enumerators.Role;
import com.stpp.movies.enumerators.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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

  @Email
  private String email;

  @Enumerated(EnumType.STRING)
//    @Pattern(regexp = "^(USER|ADMIN|GUEST)$", message = "Role should be either ADMIN or USER or GUEST")
  private Role role;

  @Enumerated(EnumType.STRING)
//    @Pattern(regexp = "^(ACTIVE|NOT_ACTIVE)$", message = "Status should be either ACTIVE or NOT_ACTIVE")
  private Status status;

  private OffsetDateTime dateCreated;
  private OffsetDateTime dateModified;
}
