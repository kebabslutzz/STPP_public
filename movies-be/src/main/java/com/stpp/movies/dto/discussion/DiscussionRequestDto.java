package com.stpp.movies.dto.discussion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class DiscussionRequestDto {
    @NotNull(message = "Title should not be null")
    @NotEmpty(message = "Title should not be empty")
    @NotBlank(message = "Title should not be blank")
    @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotNull(message = "Movie should not be null")
    private Long movieId;

    @NotNull(message = "User should not be null")
    private Long userId;
}
