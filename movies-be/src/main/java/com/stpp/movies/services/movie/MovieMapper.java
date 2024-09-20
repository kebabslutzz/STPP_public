package com.stpp.movies.services.movie;

import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieResponseDto movieToResponseDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Movie requestDtoToMovie(MovieRequestDto movieRequestDto);

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Movie editRequestDtoToMovie(MovieEditRequestDto movieEditRequestDto);

//    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "director", source = "director")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "poster", source = "poster")
    Movie movieEditRequestDtoToMovie(MovieEditRequestDto movieEditRequestDto, @MappingTarget Movie movie);
}
