package com.stpp.movies.services.discussion;

import com.stpp.movies.dto.discussion.DiscussionEditRequestDto;
import com.stpp.movies.dto.discussion.DiscussionRequestDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.entities.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface DiscussionMapper {
  DiscussionMapper INSTANCE = Mappers.getMapper(DiscussionMapper.class);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "title", source = "title")
  @Mapping(target = "movieId", source = "movie.id")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "dateCreated", source = "dateCreated")
  DiscussionResponseDto discussionToResponseDto(Discussion discussion);

  @Named("discussionEditRequestDtoToDiscussion")
  @Mapping(target = "title", source = "title")
  Discussion discussionEditRequestDtoToDiscussion(DiscussionEditRequestDto title, @MappingTarget Discussion discussion);

  @Named("discussionRequestDtoToDiscussion")
  @Mapping(target = "title", source = "discussionRequestDto.title")
  @Mapping(target = "movie.id", source = "movieId")
  @Mapping(target = "user.id", source = "discussionRequestDto.userId")
  Discussion discussionRequestDtoToDiscussion(DiscussionRequestDto discussionRequestDto, Long movieId);
}
