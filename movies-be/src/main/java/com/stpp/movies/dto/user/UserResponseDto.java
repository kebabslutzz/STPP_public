package com.stpp.movies.dto.user;

import com.stpp.movies.enumerators.Role;
import com.stpp.movies.enumerators.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class UserResponseDto {
    @NotBlank(message = "Id should not be blank")
    @NotNull(message = "Id should not be null")
    private Long id;

    @NotNull(message = "Username should not be null")
    @NotBlank(message = "Username should not be blank")
    @Size(min = 1, max = 256, message = "Username must be between {min} and {max} characters")
    private String username;

    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be blank")
    @Size(min = 1, max = 64, message = "Password must be between {min} and {max} characters")
    private String password;

    @Email
    @NotNull(message = "Email should not be null")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotNull(message = "Role should not be null")
    @Enumerated(EnumType.STRING)
//    @Pattern(regexp = "^(USER|ADMIN|GUEST)$", message = "Role should be either ADMIN or USER or GUEST")
    private Role role;

    @NotNull(message = "Status should not be null")
    @Enumerated(EnumType.STRING)
//    @Pattern(regexp = "^(ACTIVE|NOT_ACTIVE)$", message = "Status should be either ACTIVE or NOT_ACTIVE")
    private Status status;

    private OffsetDateTime dateCreated;
    private OffsetDateTime dateModified;
}
