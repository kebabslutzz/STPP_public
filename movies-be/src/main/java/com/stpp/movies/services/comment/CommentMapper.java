package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface CommentMapper {
  CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

  @Named("commentToResponseDto")
  @Mapping(source = "id", target = "Id")
  @Mapping(source = "content", target = "content")
  @Mapping(source = "discussion.id", target = "discussionId")
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user", target = "username", qualifiedByName = "mapRealUsername")
  @Mapping(source = "dateModified", target = "dateModified")
  CommentResponseDto commentToResponseDto(Comment comment);

  @Named("commentEditRequestDtoToComment")
  @Mapping(target = "content", source = "content")
  Comment commentEditRequestDtoToComment(CommentEditRequestDto content, @MappingTarget Comment comment);

  @Named("requestDtoToComment")
  @Mapping(target = "discussion.id", source = "discussionId")
  @Mapping(target = "content", source = "commentRequestDto.content")
  @Mapping(target = "user.id", source = "userId")
  Comment requestDtoToComment(CommentRequestDto commentRequestDto, Long discussionId, Long userId);

  @Named("mapRealUsername")
  default String mapRealUsername(User user) {
    return user.realUsername();
  }
}
