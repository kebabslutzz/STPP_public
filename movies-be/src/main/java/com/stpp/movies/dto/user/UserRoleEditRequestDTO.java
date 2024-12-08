package com.stpp.movies.dto.user;

import com.stpp.movies.enumerators.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class UserRoleEditRequestDTO {
  @NotNull(message = "Role should not be null")
  @Enumerated(EnumType.STRING)
  private Role role;
}
