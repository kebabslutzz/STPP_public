package com.stpp.movies.controllers;

import com.stpp.movies.dto.ErrorResponseDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.services.comment.CommentService;
import com.stpp.movies.services.discussion.DiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Discussion Operations", description = "Some more operations on discussions")
public class DiscussionController {

  private final DiscussionService discussionService;
  private final CommentService commentService;

  @Operation(summary = "Get a discussion by id", description = "Get a discussion by id", responses = {
    @ApiResponse(responseCode = "200", description = "Discussion found"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/{discussionId}")
  public DiscussionResponseDto getDiscussionById(@Valid @PathVariable Long discussionId) {
    return discussionService.getDiscussionById(discussionId);
  }

  @Operation(summary = "Get all discussions", description = "Get all discussions", responses = {
    @ApiResponse(responseCode = "200", description = "Discussions found"),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping
  public List<DiscussionResponseDto> getAllDiscussions() {
    return discussionService.getAllDiscussions();
  }

  @Operation(summary = "Get all comments by discussion id", description = "Get all comments by discussion id", responses = {
    @ApiResponse(responseCode = "200", description = "Comments found"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/{discussionId}/comments")
  public List<CommentResponseDto> getAllCommentsByDiscussionId(@Valid @PathVariable Long discussionId) {
    return commentService.getAllByDiscussionId(discussionId);
  }

  @Operation(summary = "Delete a discussion by id", description = "Delete a discussion by id", responses = {
    @ApiResponse(responseCode = "204", description = "Discussion deleted"),
    @ApiResponse(responseCode = "404", description = "Discussion not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{discussionId}")
  public void deleteDiscussionById(@Valid @PathVariable Long discussionId) {
    discussionService.deleteDiscussionById(discussionId);
  }
}
