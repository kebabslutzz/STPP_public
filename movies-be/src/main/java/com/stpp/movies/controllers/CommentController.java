package com.stpp.movies.controllers;

import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.services.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "Get a comment by id", description = "Get a comment by id", tags = {"comments"}, responses = {
    @ApiResponse(responseCode = "200", description = "Comment found"),
    @ApiResponse(responseCode = "404", description = "Comment not found")
  })
  @GetMapping("/{id}")
  public CommentResponseDto getCommentById(@PathVariable Long id) {
    return commentService.getCommentByMovieIdAndDiscussionIdAndCommentId(id);
  }

  @Operation(summary = "Get all comments", description = "Get all comments", tags = {"comments"}, responses = {
    @ApiResponse(responseCode = "200", description = "Comments found")
  })
  @GetMapping
  public List<CommentResponseDto> getAllComments() {
    return commentService.getAllComments();
  }

  @Operation(summary = "Delete a comment by id", description = "Delete a comment by id", tags = {"comments"}, responses = {
    @ApiResponse(responseCode = "204", description = "No Content"),
    @ApiResponse(responseCode = "404", description = "Comment not Found")
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
  }
}
