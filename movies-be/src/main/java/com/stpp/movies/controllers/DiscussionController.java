package com.stpp.movies.controllers;

import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.services.comment.CommentService;
import com.stpp.movies.services.discussion.DiscussionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discussions")
@RequiredArgsConstructor
@Validated
public class DiscussionController {

    private final DiscussionService discussionService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionResponseDto> getDiscussionById(@Valid @PathVariable Long id){
        return discussionService
                .getDiscussionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DiscussionResponseDto>> getAllDiscussions(){
        List<DiscussionResponseDto> discussions = discussionService.getAllDiscussions();
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByDiscussionId(@Valid @PathVariable Long id) {
        List<CommentResponseDto> comments = commentService.getAllByDiscussionId(id);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussionById(@Valid @PathVariable Long id) {
        discussionService.deleteDiscussionById(id);
        return ResponseEntity.noContent().build();
    }
}
