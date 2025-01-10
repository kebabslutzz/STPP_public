package com.stpp.movies.dto.discussion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class DiscussionEditRequestDto {
  @NotEmpty(message = "Title should not be empty")
  @NotBlank(message = "Title should not be blank")
  @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
  private String title;
}
