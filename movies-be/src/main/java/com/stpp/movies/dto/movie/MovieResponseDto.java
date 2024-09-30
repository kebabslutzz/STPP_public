package com.stpp.movies.dto.movie;

import com.stpp.movies.entities.Movie;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
public class MovieResponseDto {
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
    private Double rating;

    @NotNull(message = "Release date should not be null")
    private LocalDate releaseDate;

//    private byte[] poster;
    @Nullable
    private Long posterId;
//    private String poster;

//    public static MovieResponseDto of(Movie movie) {
//        return MovieResponseDto.builder()
//                .id(movie.getId())
//                .title(movie.getTitle())
//                .description(movie.getDescription())
//                .director(movie.getDirector())
//                .genre(movie.getGenre())
//                .rating(movie.getRating())
//                .releaseDate(movie.getReleaseDate())
//                .poster(movie.getPoster())
//                .build();
//    }
}
