package com.stpp.movies.services.discussion;

import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.entities.Discussion;
import com.stpp.movies.services.comment.CommentMapper;
import com.stpp.movies.services.movie.MovieMapper;
import com.stpp.movies.services.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, MovieMapper.class, CommentMapper.class})
public interface DiscussionMapper {
    DiscussionMapper INSTANCE = Mappers.getMapper(DiscussionMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "userId", source = "user.id")
    DiscussionResponseDto discussionToResponseDto(Discussion discussion);
}
