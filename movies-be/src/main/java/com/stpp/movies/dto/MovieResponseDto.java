package com.stpp.movies.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stpp.movies.configurations.LocalDateDeserializer;
import com.stpp.movies.entities.Movie;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class MovieResponseDto {
    @NotBlank(message = "Id should not be blank")
    @NotNull(message = "Id should not be null")
    private Long id;

    @NotBlank(message = "Title should not be blank")
    @NotEmpty(message = "Title should not be empty")
    @NotNull(message = "Title should not be null")
    @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Description should not be blank")
    @NotEmpty(message = "Description should not be empty")
    @NotNull(message = "Description should not be null")
    @Size(min = 1, max = 1024, message = "Description must be between {min} and {max} characters")
    private String description;

    @NotBlank(message = "Director must not be blank")
    @NotEmpty(message = "Director must not be empty")
    @NotNull(message = "Director must not be null")
    @Size(min = 1, max = 256, message = "Director must be between {min} and {max} characters")
    private String director;

    @NotBlank(message = "Genre must not be blank")
    @NotEmpty(message = "Genre must not be empty")
    @NotNull(message = "Genre must not be null")
    @Size(min = 1, max = 256, message = "Genre must be between {min} and {max} characters")
    private String genre;

    @NotNull(message = "Rating should not be null")
    @Min(value = 1, message = "Rating should be between 1 and 10")
    @Max(value = 10, message = "Rating should be between 1 and 10")
    private Float rating;

    @NotNull(message = "Release date should not be null")
    private LocalDate releaseDate;

    private byte[] poster;

    public static MovieResponseDto of(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .poster(movie.getPoster())
                .build();
    }
}
