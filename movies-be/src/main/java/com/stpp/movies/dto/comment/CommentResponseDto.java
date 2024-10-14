package com.stpp.movies.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CommentResponseDto {
  private Long Id;
  private String content;
  private Long discussionId;
  private Long userId;
  private OffsetDateTime dateModified;
}
