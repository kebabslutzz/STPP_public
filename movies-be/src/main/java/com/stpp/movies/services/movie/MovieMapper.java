package com.stpp.movies.services.movie;
import com.stpp.movies.dto.MovieEditRequestDto;
import com.stpp.movies.dto.MovieRequestDto;
import com.stpp.movies.dto.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
}
