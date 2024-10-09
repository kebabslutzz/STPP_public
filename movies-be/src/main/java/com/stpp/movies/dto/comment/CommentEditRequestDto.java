package com.stpp.movies.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class CommentEditRequestDto {
    @NotBlank(message = "Content should not be blank")
    @NotNull(message = "Content should not be null")
    @Size(min = 1, max = 1024, message = "Content must be between {min} and {max} characters")
    private String content;
}
