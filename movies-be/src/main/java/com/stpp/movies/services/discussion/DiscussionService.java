package com.stpp.movies.services.discussion;

import com.stpp.movies.dto.discussion.DiscussionEditRequestDto;
import com.stpp.movies.dto.discussion.DiscussionRequestDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.entities.Discussion;
import com.stpp.movies.enumerators.Role;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.DiscussionRepository;
import com.stpp.movies.repositories.MovieRepository;
import com.stpp.movies.repositories.UserRepository;
import com.stpp.movies.services.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Validated
public class DiscussionService {
  private final DiscussionRepository discussionRepository;
  private final MovieRepository movieRepository;
  private final UserRepository userRepository;
  private final CommentService commentService;
  private static final DiscussionMapper MAPPER = DiscussionMapper.INSTANCE;

  public List<DiscussionResponseDto> getAllDiscussionsByMovieId(Long movieId) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    return discussionRepository
      .findAllByMovieId(movieId)
      .stream()
      .map(discussion -> {
        int commentCount = commentService.getNumberOfCommentsByDiscussionId(discussion.getId());
        DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion);
        discussionResponseDto.setCommentCount(commentCount);
        return discussionResponseDto;
      })
      .toList();
  }

  public DiscussionResponseDto getDiscussionById(Long id) {
    return discussionRepository.findById(id)
      .map(MAPPER::discussionToResponseDto)
      .map(discussion -> {
        discussion.setCommentCount(commentService.getAllByDiscussionId(id).size());
        return discussion;
      }).orElseThrow(() -> new NotFoundException("Discussion with ID " + id + " not found"));
  }

  public DiscussionResponseDto getDiscussionByIMovieAndDiscussionId(Long movieId, Long discussionId) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    return discussionRepository.findById(discussionId)
      .filter(discussion -> discussion.getMovie().getId().equals(movieId))
      .map(MAPPER::discussionToResponseDto)
      .map(discussion1 -> {
        discussion1.setCommentCount(commentService.getAllByDiscussionId(discussion1.getId()).size());
        return discussion1;
      }).orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found or does not belong to movie with ID " + movieId));
  }

  public List<DiscussionResponseDto> getAllDiscussions() {
    return discussionRepository
      .findAllByOrderByMovieAsc()
      .stream()
      .map(discussion -> {
        DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion);
        discussionResponseDto.setCommentCount(commentService.getNumberOfCommentsByDiscussionId(discussion.getId()));
        return discussionResponseDto;
      })
      .toList();
  }

  public DiscussionResponseDto editDiscussion(@Valid Long movieId, @Valid Long discussionId, @Valid DiscussionEditRequestDto discussionEditRequestDto, Long userId) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }

    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));

    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " does not belong to movie with ID " + movieId);
    }

    if (!discussion.getUser().getId().equals(userId)) {
      throw new AccessDeniedException("User is not authorized to edit this discussion");
    }

    MAPPER.discussionEditRequestDtoToDiscussion(discussionEditRequestDto, discussion);
    discussion = discussionRepository.save(discussion);
    return MAPPER.discussionToResponseDto(discussion);
  }

  public void deleteDiscussionById(Long id) {
    if (!discussionRepository.existsById(id)) {
      throw new NotFoundException("Discussion with ID " + id + " not found");
    }
    discussionRepository.deleteById(id);
  }

  public void deleteDiscussionByMovieAndDiscussionId(Long movieId, Long discussionId, Long userId, Role userRole) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }

    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));

    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " does not belong to movie with ID " + movieId);
    }

    if (!discussion.getUser().getId().equals(userId) && !userRole.equals(Role.ADMIN)) {
      throw new AccessDeniedException("User is not authorized to delete this discussion");
    }

    discussionRepository.deleteById(discussionId);
  }

  public DiscussionResponseDto createDiscussion(Long movieId, DiscussionRequestDto discussionRequestDto, Long userId) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException("User not found");
    }

    Discussion discussion = MAPPER.discussionRequestDtoToDiscussion(discussionRequestDto, movieId, userId);
    discussion = discussionRepository.save(discussion);
    return MAPPER.discussionToResponseDto(discussion);
  }
}
