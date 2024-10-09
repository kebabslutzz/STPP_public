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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/movies")
public class MovieController{

    private final MovieService movieService;
    private final DiscussionService discussionService;
    private final CommentService commentService;

//    Create Movie CRUD
    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@Valid @RequestBody MovieRequestDto movieRequestDto){
        MovieResponseDto movie = movieService.createMovie(movieRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(location).body(movie);
    }

//    Read Movie CRUD
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id){
        return movieService
                .getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    Read All Movies CRUD
    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(){
        List<MovieResponseDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

//    Update Movie CRUD
    @PutMapping
    public ResponseEntity<?> editMovie(@Valid @RequestBody MovieEditRequestDto movieEditRequestDto) {
        MovieResponseDto updatedMovie = movieService.editMovie(movieEditRequestDto);
        return ResponseEntity.ok(updatedMovie);
    }

//    Add Poster to Movie
    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponseDto> addPosterToMovie(@PathVariable Long id, @Valid @RequestBody Long posterId){
        MovieResponseDto movie = movieService.addPosterToMovie(id, posterId);
        return ResponseEntity.ok(movie);
    }

//    Delete Movie CRUD
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/discussions")
    public ResponseEntity<List<DiscussionResponseDto>> getAllDiscussionsByMovieId(@PathVariable @Valid Long id){
        List<DiscussionResponseDto> discussions = discussionService.getAllDiscussionsByMovieId(id);
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/{movieId}/discussions/{discussionId}")
    public ResponseEntity<DiscussionResponseDto> getDiscussionByMovieAndDiscussionId(@PathVariable Long movieId, @PathVariable Long discussionId){
        return discussionService
                .getDiscussionByIMovieAndDiscussiond(movieId, discussionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/discussions")
    public ResponseEntity<DiscussionResponseDto> createDiscussion(@PathVariable Long id, @Valid @RequestBody DiscussionRequestDto discussionRequestDto){
        DiscussionResponseDto createdDiscussion = discussionService.createDiscussion(id, discussionRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDiscussion.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdDiscussion);
    }

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

    @PatchMapping("/{movieId}/discussions/{discussionId}")
    public ResponseEntity<DiscussionResponseDto> editDiscussion(@Valid @PathVariable Long movieId, @Valid @PathVariable Long discussionId, @Valid @RequestBody DiscussionEditRequestDto discussionEditRequestDto) {
        return ResponseEntity.ok(discussionService.editDiscussion(movieId, discussionId, discussionEditRequestDto));
    }

    @PatchMapping("/{movieId}/discussions/{discussionId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> editComment(@Valid @PathVariable Long movieId, @Valid @PathVariable Long discussionId, @Valid @PathVariable Long commentId, @Valid @RequestBody CommentEditRequestDto commentEditRequestDto){
        return ResponseEntity.ok(commentService.editComment(movieId, discussionId, commentId, commentEditRequestDto));
    }

    @GetMapping("/{movieId}/discussions/{discussionId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByDiscussionId(@PathVariable Long movieId, @PathVariable Long discussionId){
        List<CommentResponseDto> comments = commentService.getAllCommentsByDiscussionId(movieId, discussionId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{movieId}/discussions/{discussionId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long movieId, @PathVariable Long discussionId, @PathVariable Long commentId){
        commentService.deleteComment(movieId, discussionId, commentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{movieId}/discussions/{discussionId}")
    public ResponseEntity<Void> deleteDiscussionById(@PathVariable Long movieId, @PathVariable Long discussionId){
        discussionService.deleteDiscussionById(movieId, discussionId);
        return ResponseEntity.noContent().build();
    }
}
