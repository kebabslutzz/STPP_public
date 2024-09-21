package com.stpp.movies.dto.discussion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.entities.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class DiscussionResponseDto {
    @NotBlank(message = "Id should not be blank")
    @NotNull(message = "Id should not be null")
    private Long id;

    @NotNull(message = "Title should not be null")
    @NotEmpty(message = "Title should not be empty")
    @NotBlank(message = "Title should not be blank")
    @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
    private String title;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private List<Comment> comments;

    @NotNull(message = "Movie should not be null")
    @NotBlank(message = "Movie should not be blank")
    private Long movieId;

    @NotNull(message = "User should not be null")
    @NotNull(message = "User should not be blank")
    private Long userId;
}
