package com.stpp.movies.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class CommentRequestDto {
  @NotBlank(message = "Content should not be blank")
  @NotEmpty(message = "Content should not be empty")
  @Size(min = 1, max = 1024, message = "Content must be between {min} and {max} characters")
  private String content;

  @NotNull(message = "User id should not be null")
  private Long userId;
}
