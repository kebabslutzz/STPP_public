package com.stpp.movies.dto.user;

import com.stpp.movies.enumerators.Role;
import com.stpp.movies.enumerators.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserRequestDto {
    @NotBlank(message = "Username should not be blank")
    @Size(min = 1, max = 256, message = "Username must be between {min} and {max} characters")
    private String username;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, max = 64, message = "Password must be between {min} and {max} characters")
    private String password;

    @Email
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
}
