package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.services.discussion.DiscussionMapper;
import com.stpp.movies.services.movie.MovieMapper;
import com.stpp.movies.services.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {MovieMapper.class, UserMapper.class, DiscussionMapper.class})
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Named("commentToResponseDto")
    @Mapping(source = "id", target = "Id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "discussion.id", target = "discussion_id")
    @Mapping(source = "user.id", target = "user_id")
    CommentResponseDto commentToResponseDto(Comment comment);

    @Named("commentEditRequestDtoToComment")
    @Mapping(target = "content", source = "content")
    Comment commentEditRequestDtoToComment(String content, @MappingTarget Comment comment);

    @Named("requestDtoToComment")
    @Mapping(target = "discussion.id", source = "discussionId")
    @Mapping(target = "content", source = "commentRequestDto.content")
    @Mapping(target = "user.id", source = "commentRequestDto.userId")
    Comment requestDtoToComment(CommentRequestDto commentRequestDto, Long discussionId);

//    @Mapping(target = "content", source = "content")
//    Comment requestDtoToComment(String content);
}
