package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "Id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "discussion.id", target = "discussion_id")
    @Mapping(source = "user.id", target = "user_id")
    CommentResponseDto commentToResponseDto(Comment comment);

    @Mapping(target = "content", source = "content")
    Comment commentEditRequestDtoToComment(String content, @MappingTarget Comment comment);

//    @Mapping(target = "content", source = "content")
//    Comment requestDtoToComment(String content);
}
