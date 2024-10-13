package com.stpp.movies.controllers;

import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.services.comment.CommentService;
import com.stpp.movies.services.discussion.DiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/discussions")
@RequiredArgsConstructor
@Validated
public class DiscussionController {

  private final DiscussionService discussionService;
  private final CommentService commentService;

  @Operation(summary = "Get a discussion by id", description = "Get a discussion by id", tags = {"discussions"}, responses = {
    @ApiResponse(responseCode = "200", description = "Discussion found"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found")
  })
  @GetMapping("/{id}")
  public DiscussionResponseDto getDiscussionById(@Valid @PathVariable Long id) {
    return discussionService.getDiscussionById(id);
  }

  @Operation(summary = "Get all discussions", description = "Get all discussions", tags = {"discussions"}, responses = {
    @ApiResponse(responseCode = "200", description = "Discussions found")
  })
  @GetMapping
  public List<DiscussionResponseDto> getAllDiscussions() {
    return discussionService.getAllDiscussions();
  }

  @Operation(summary = "Get all comments by discussion id", description = "Get all comments by discussion id", tags = {"discussions"}, responses = {
    @ApiResponse(responseCode = "200", description = "Comments found"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found")
  })
  @GetMapping("/{id}/comments")
  public List<CommentResponseDto> getAllCommentsByDiscussionId(@Valid @PathVariable Long id) {
    return commentService.getAllByDiscussionId(id);
  }

  @Operation(summary = "Delete a discussion by id", description = "Delete a discussion by id", tags = {"discussions"}, responses = {
    @ApiResponse(responseCode = "204", description = "Discussion deleted"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found")
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteDiscussionById(@Valid @PathVariable Long id) {
    discussionService.deleteDiscussionById(id);
  }
}
