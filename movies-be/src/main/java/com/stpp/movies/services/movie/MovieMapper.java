package com.stpp.movies.services.movie;

import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.services.comment.CommentMapper;
import com.stpp.movies.services.discussion.DiscussionMapper;
import com.stpp.movies.services.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, CommentMapper.class, DiscussionMapper.class})
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Named("movieToResponseDto")
    MovieResponseDto movieToResponseDto(Movie movie);

    @Named("requestDtoToMovie")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Movie requestDtoToMovie(MovieRequestDto movieRequestDto);

    @Named("editRequestDtoToMovie")
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Movie editRequestDtoToMovie(MovieEditRequestDto movieEditRequestDto);

    @Named("editRequestDtoToMovie")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "director", source = "director")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "poster", source = "poster")
    Movie movieEditRequestDtoToMovie(MovieEditRequestDto movieEditRequestDto, @MappingTarget Movie movie);
}
