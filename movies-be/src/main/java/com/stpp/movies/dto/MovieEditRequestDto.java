package com.stpp.movies.dto;

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

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class MovieEditRequestDto {
    @NotNull(message = "Id should not be null")
    @NotNull(message = "Id should not be null")
    private Long id;

    @NotBlank(message = "Title should not be blank")
    @NotEmpty(message = "Title should not be empty")
    @NotNull(message = "Title should not be null")
    @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    @NotBlank(message = "Description should not be blank")
    @NotNull(message = "Description should not be null")
    @Size(min = 1, max = 1024, message = "Description must be between {min} and {max} characters")
    private String description;

    @NotBlank(message = "Director should not be blank")
    @NotEmpty(message = "Director should not be empty")
    @NotNull(message = "Director should not be null")
    @Size(min = 1, max = 256, message = "Director must be between {min} and {max} characters")
    private String director;

    @NotBlank(message = "Genre should not be blank")
    @NotEmpty(message = "Genre should not be empty")
    @NotNull(message = "Genre should not be null")
    @Size(min = 1, max = 256, message = "Genre must be between {min} and {max} characters")
    private String genre;

    @NotNull
    @Min(value = 1, message = "Rating should be between 1 and 10")
    @Max(value = 10, message = "Rating should be between 1 and 10")
    private Float rating;

    @NotNull(message = "Release date should not be null")
    private LocalDate releaseDate;

    private byte[] poster;
}
