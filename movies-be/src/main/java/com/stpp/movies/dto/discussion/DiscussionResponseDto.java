package com.stpp.movies.dto.discussion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class DiscussionResponseDto {
  private Long id;
  private String title;
  private Long movieId;
  private Long userId;
  private OffsetDateTime dateCreated;
  private int commentCount;
}
