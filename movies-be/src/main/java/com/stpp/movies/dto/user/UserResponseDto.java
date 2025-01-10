package com.stpp.movies.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stpp.movies.enumerators.Role;
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
  private String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String token;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Role role;

  private OffsetDateTime dateCreated;
  private OffsetDateTime dateModified;
}
