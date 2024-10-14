package com.stpp.movies.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class MovieResponseDto {
  private Long id;
  private String title;
  private String description;
  private String director;
  private String genre;
  private Double rating;
  private LocalDate releaseDate;
  private Long posterId;
}
