package com.stpp.movies.controllers;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.dto.discussion.DiscussionEditRequestDto;
import com.stpp.movies.dto.discussion.DiscussionRequestDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.services.comment.CommentService;
import com.stpp.movies.services.discussion.DiscussionService;
import com.stpp.movies.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final MovieService movieService;
  private final DiscussionService discussionService;
  private final CommentService commentService;

  /**
   * ************ MOVIE CRUDS *************
   **/
  @Operation(summary = "Create a new movie", description = "Create a new movie", responses = {
    @ApiResponse(responseCode = "201", description = "Movie created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PostMapping
  public ResponseEntity<MovieResponseDto> createMovie(@Valid @RequestBody MovieRequestDto movieRequestDto) {
    MovieResponseDto movie = movieService.createMovie(movieRequestDto);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(movie.getId())
      .toUri();
    return ResponseEntity.created(location).body(movie);
  }

  @Operation(summary = "Get a movie by id", description = "Get a movie by id", responses = {
    @ApiResponse(responseCode = "200", description = "Movie found"),
    @ApiResponse(responseCode = "404", description = "Movie not found"),
  })
  @GetMapping("/{id}")
  public MovieResponseDto getMovieById(@PathVariable Long id) {
    return movieService.getMovieById(id);
  }

  @Operation(summary = "Get all movies", description = "Get all movies", responses = {
    @ApiResponse(responseCode = "200", description = "Movies found"),
    @ApiResponse(responseCode = "404", description = "Movies not found"),
  })
  @GetMapping
  public List<MovieResponseDto> getAllMovies() {
    return movieService.getAllMovies();
  }

  @Operation(summary = "Edit a movie", description = "Edit a movie", responses = {
    @ApiResponse(responseCode = "200", description = "Movie edited successfully"),
    @ApiResponse(responseCode = "404", description = "Movie not found"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PutMapping("/{id}")
  public MovieResponseDto editMovie(@Valid @PathVariable Long id, @Valid @RequestBody MovieEditRequestDto movieEditRequestDto) {
    return movieService.editMovie(id, movieEditRequestDto);
  }

  //NOT USED!
  @Operation(summary = "Add a poster to a movie", description = "Add a poster to a movie", responses = {
    @ApiResponse(responseCode = "200", description = "Poster added successfully"),
    @ApiResponse(responseCode = "404", description = "Movie not found"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PatchMapping("/{id}")
  public MovieResponseDto addPosterToMovie(@PathVariable Long id, @Valid @RequestBody Long posterId) {
    return movieService.addPosterToMovie(id, posterId);
  }

  @Operation(summary = "Delete a movie by id", description = "Delete a movie by id", responses = {
    @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Movie not found"),
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteMovieById(@PathVariable Long id) {
    movieService.deleteMovieById(id);
  }

  /**
   * ************ DISCUSSION CRUDS *************
   **/
  @Operation(summary = "Get all discussions by movie id", description = "Get all discussions by movie id", responses = {
    @ApiResponse(responseCode = "200", description = "Discussions found"),
    @ApiResponse(responseCode = "404", description = "Discussions not found"),
  })
  @GetMapping("/{id}/discussions")
  public List<DiscussionResponseDto> getAllDiscussionsByMovieId(@PathVariable @Valid Long id) {
    return discussionService.getAllDiscussionsByMovieId(id);
  }

  @Operation(summary = "Get a discussion by movie and discussion id", description = "Get a discussion by movie and discussion id", responses = {
    @ApiResponse(responseCode = "200", description = "Discussion found"),
    @ApiResponse(responseCode = "404", description = "Discussion not found"),
  })
  @GetMapping("/{movieId}/discussions/{discussionId}")
  public DiscussionResponseDto getDiscussionByMovieAndDiscussionId(@PathVariable Long movieId, @PathVariable Long discussionId) {
    return discussionService.getDiscussionByIMovieAndDiscussionId(movieId, discussionId);
  }

  @Operation(summary = "Create a new discussion", description = "Create a new discussion", responses = {
    @ApiResponse(responseCode = "201", description = "Discussion created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PostMapping("/{id}/discussions")
  public ResponseEntity<DiscussionResponseDto> createDiscussion(@PathVariable Long id, @Valid @RequestBody DiscussionRequestDto discussionRequestDto) {
    DiscussionResponseDto createdDiscussion = discussionService.createDiscussion(id, discussionRequestDto);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(createdDiscussion.getId())
      .toUri();
    return ResponseEntity.created(location).body(createdDiscussion);
  }

  @Operation(summary = "Edit a discussion", description = "Edit a discussion", responses = {
    @ApiResponse(responseCode = "200", description = "Discussion edited successfully"),
    @ApiResponse(responseCode = "404", description = "Discussion not found"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PatchMapping("/{movieId}/discussions/{discussionId}")
  public DiscussionResponseDto editDiscussion(@Valid @PathVariable Long movieId, @Valid @PathVariable Long discussionId, @Valid @RequestBody DiscussionEditRequestDto discussionEditRequestDto) {
    return discussionService.editDiscussion(movieId, discussionId, discussionEditRequestDto);
  }

  @Operation(summary = "Delete a discussion by movie and discussion id", description = "Delete a discussion by movie and discussion id", responses = {
    @ApiResponse(responseCode = "204", description = "Discussion deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Discussion not found"),
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{movieId}/discussions/{discussionId}")
  public void deleteDiscussionById(@PathVariable Long movieId, @PathVariable Long discussionId) {
    discussionService.deleteDiscussionByMovieAndDiscussionId(movieId, discussionId);
  }

  /**
   * ************ COMMENT CRUDS *************
   **/
  @Operation(summary = "Get a comment by movie, discussion and comment id", description = "Get a comment by movie, discussion and comment id", responses = {
    @ApiResponse(responseCode = "200", description = "Comment found"),
    @ApiResponse(responseCode = "404", description = "Comment not found"),
  })
  @GetMapping("/{movieId}/discussions/{discussionId}/comments/{commentId}")
  public CommentResponseDto getCommentById(@PathVariable Long movieId, @PathVariable Long discussionId, @PathVariable Long commentId) {
    return commentService.getCommentByMovieIdAndDiscussionIdAndCommentId(movieId, discussionId, commentId);
  }

  @Operation(summary = "Get all comments by discussion id", description = "Get all comments by discussion id", responses = {
    @ApiResponse(responseCode = "200", description = "Comments found"),
    @ApiResponse(responseCode = "404", description = "Comments not found"),
  })
  @GetMapping("/{movieId}/discussions/{discussionId}/comments")
  public List<CommentResponseDto> getAllCommentsByDiscussionId(@PathVariable Long movieId, @PathVariable Long discussionId) {
    return commentService.getAllCommentsByMovieIdAndDiscussionId(movieId, discussionId);
  }

  @Operation(summary = "Create a new comment", description = "Create a new comment", responses = {
    @ApiResponse(responseCode = "201", description = "Comment created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PostMapping("/{movieId}/discussions/{discussionId}/comments")
  public ResponseEntity<CommentResponseDto> createComment(@Valid @PathVariable Long movieId, @Valid @PathVariable Long discussionId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
    CommentResponseDto createdComment = commentService.createComment(movieId, discussionId, commentRequestDto);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(createdComment.getId())
      .toUri();
    return ResponseEntity.created(location).body(createdComment);
  }

  @Operation(summary = "Edit a comment", description = "Edit a comment", responses = {
    @ApiResponse(responseCode = "200", description = "Comment edited successfully"),
    @ApiResponse(responseCode = "404", description = "Comment not found"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
  })
  @PatchMapping("/{movieId}/discussions/{discussionId}/comments/{commentId}")
  public CommentResponseDto editComment(@Valid @PathVariable Long movieId, @Valid @PathVariable Long discussionId, @Valid @PathVariable Long commentId, @Valid @RequestBody CommentEditRequestDto commentEditRequestDto) {
    return commentService.editComment(movieId, discussionId, commentId, commentEditRequestDto);
  }

  @Operation(summary = "Delete a comment by movie, discussion and comment id", description = "Delete a comment by movie, discussion and comment id", responses = {
    @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
    @ApiResponse(
      responseCode = "404",
      description = "Movie not found or Discussion not found or Comment not found or Discussion not found in movie or Comment not found in discussion"),
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{movieId}/discussions/{discussionId}/comments/{commentId}")
  public void deleteCommentById(@PathVariable Long movieId, @PathVariable Long discussionId, @PathVariable Long commentId) {
    commentService.deleteComment(movieId, discussionId, commentId);
  }
}
