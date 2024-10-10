package com.stpp.movies.services.movie;

import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.services.comment.CommentMapper;
import com.stpp.movies.services.discussion.DiscussionMapper;
import com.stpp.movies.services.user.UserMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

@Mapper(uses = {UserMapper.class, CommentMapper.class, DiscussionMapper.class})
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Named("movieToResponseDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "director", target = "director")
    @Mapping(source = "genre", target = "genre")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "releaseDate", target = "releaseDate")
    @Mapping(source = "poster.id", target = "posterId")
    MovieResponseDto movieToResponseDto(Movie movie);

    @Named("requestDtoToMovie")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    @Mapping(source = "posterId", target = "poster.id", ignore = true)
    Movie requestDtoToMovie(MovieRequestDto movieRequestDto);

    @Named("editRequestDtoToMovie")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "director", source = "director")
    @Mapping(target = "genre", source = "genre")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "releaseDate", source = "releaseDate")
//    @Mapping(target = "poster.id", source = "posterId")
    Movie movieEditRequestDtoToMovie(MovieEditRequestDto movieEditRequestDto, @MappingTarget Movie movie);
}
