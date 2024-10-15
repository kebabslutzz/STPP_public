package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.entities.Discussion;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.CommentRepository;
import com.stpp.movies.repositories.DiscussionRepository;
import com.stpp.movies.repositories.MovieRepository;
import com.stpp.movies.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Validated
public class CommentService {

  private final CommentRepository commentRepository;
  private final MovieRepository movieRepository;
  private final UserRepository userRepository;
  private final DiscussionRepository discussionRepository;

  private static final CommentMapper MAPPER = CommentMapper.INSTANCE;

  public CommentResponseDto getCommentByMovieIdAndDiscussionIdAndCommentId(Long id) {
    return commentRepository.findById(id)
      .map(MAPPER::commentToResponseDto)
      .orElseThrow(() -> new NotFoundException("Comment with ID " + id + " not found"));
  }

  public List<CommentResponseDto> getAllComments() {
    return commentRepository
      .findAll()
      .stream()
      .map(MAPPER::commentToResponseDto)
      .toList();
  }

  public CommentResponseDto editComment(@Valid long movieId, @Valid long discussionId, @Valid long commentId, CommentEditRequestDto commentEditRequestDto) {
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(() -> new NotFoundException("Comment with ID " + commentId + " not found"));
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));
    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " not found in movie with ID " + movieId);
    }
    if (!comment.getDiscussion().getId().equals(discussionId)) {
      throw new NotFoundException("Comment with ID " + commentId + " not found in discussion with ID " + discussionId);
    }

    MAPPER.commentEditRequestDtoToComment(commentEditRequestDto, comment);
    return MAPPER.commentToResponseDto(comment);
  }

  public void deleteComment(@Valid Long movieId, @Valid Long discussionId, @Valid Long id) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));
    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " not found in movie with ID " + movieId);
    }
    Comment comment = commentRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("Comment with ID " + id + " not found"));
    if (!comment.getDiscussion().getId().equals(discussionId)) {
      throw new NotFoundException("Comment with ID " + id + " not found in discussion with ID " + discussionId);
    }
    commentRepository.deleteById(id);
  }

  public void deleteComment(@Valid Long id) {
    if (!commentRepository.existsById(id)) {
      throw new NotFoundException("Comment with ID " + id + " not found");
    }
    commentRepository.deleteById(id);
  }

  public List<CommentResponseDto> getAllByDiscussionId(Long id) {
    return commentRepository
      .findAllByDiscussionId(id)
      .stream()
      .map(MAPPER::commentToResponseDto)
      .toList();
  }

  public CommentResponseDto createComment(Long movieId, Long discussionId, CommentRequestDto content) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));
    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " not found in movie with ID " + movieId);
    }
    if (!userRepository.existsById(content.getUserId())) {
      throw new NotFoundException("User with ID " + content.getUserId() + " not found");
    }

    Comment comment = MAPPER.requestDtoToComment(content, discussionId);
    comment = commentRepository.save(comment);
    return MAPPER.commentToResponseDto(comment);
  }

  public List<CommentResponseDto> getAllCommentsByMovieIdAndDiscussionId(Long movieId, Long discussionId) {
    if (!movieRepository.existsById(movieId)) {
      throw new NotFoundException("Movie with ID " + movieId + " not found");
    }
    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));
    if (!discussion.getMovie().getId().equals(movieId)) {
      throw new NotFoundException("Discussion with ID " + discussionId + " not found in movie with ID " + movieId);
    }
    return getAllByDiscussionId(discussionId);
  }

  public int getNumberOfCommentsByDiscussionId(Long discussionId) {
    return commentRepository.findAllByDiscussionId(discussionId).size();
  }

  public CommentResponseDto getCommentByMovieIdAndDiscussionIdAndCommentId(Long movieId, Long discussionId, Long commentId) {

    Movie movie = movieRepository.findById(movieId)
      .orElseThrow(() -> new NotFoundException("Movie with ID " + movieId + " not found"));

    Discussion discussion = discussionRepository.findById(discussionId)
      .orElseThrow(() -> new NotFoundException("Discussion with ID " + discussionId + " not found"));

    CommentResponseDto commentResponseDto = commentRepository.findById(commentId)
      .map(MAPPER::commentToResponseDto)
      .orElseThrow(() -> new NotFoundException("Comment with ID " + commentId + " not found"));

    if (!discussion.getMovie().getId().equals(movie.getId())) {
      throw new NotFoundException("Discussion with ID " + discussionId + " not found in movie with ID " + movieId);
    }
    if (!commentResponseDto.getDiscussionId().equals(discussion.getId())) {
      throw new NotFoundException("Comment with ID " + commentId + " not found in discussion with ID " + discussionId);
    }

    return commentResponseDto;
  }
}
