package com.stpp.movies.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stpp.movies.configurations.LocalDateDeserializer;
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
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class MovieRequestDto {
    @NotBlank(message = "Title should not be blank")
    @NotNull(message = "Title should not be null")
    @Size(min = 1, max = 256, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Description should not be blank")
    @NotEmpty(message = "Description should not be empty")
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

    @NotNull(message = "Rating should not be null")
    @Min(value = 1, message = "Rating should be between 1 and 10")
    @Max(value = 10, message = "Rating should be between 1 and 10")
    private Float rating;

    @NotNull(message = "Release date should not be null")
    private LocalDate releaseDate;

    private byte[] poster;
}
