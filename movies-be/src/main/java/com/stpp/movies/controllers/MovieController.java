package com.stpp.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.dto.discussion.DiscussionRequestDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.exceptions.ValidationException;
import com.stpp.movies.services.comment.CommentService;
import com.stpp.movies.services.discussion.DiscussionService;
import com.stpp.movies.services.movie.MovieService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/movies")
public class MovieController{

    private final MovieService movieService;
    private final DiscussionService discussionService;
    private final CommentService commentService;

    private final Validator validator; // Inject a Validator instance

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id){
        return movieService
                .getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

//    @PatchMapping(value = "/{id}/poster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<MovieResponseDto> updateMoviePoster(
//            @PathVariable Long id,
//            @RequestPart("poster") MultipartFile poster) throws IOException {
//
//        MovieResponseDto movie = movieService.updateMoviePoster(id, poster.getBytes());
//        return ResponseEntity.ok(movie);
//    }



//    @PostMapping
//    public ResponseEntity<MovieResponseDto> createMovie(
//            @Valid @RequestPart("movie") String movieRequestDtoStr,
//            @RequestPart("poster") MultipartFile poster) throws IOException, MethodArgumentNotValidException {
////            @RequestParam("poster") MultipartFile poster) throws IOException, MethodArgumentNotValidException {
//
//        objectMapper.registerModule(new JavaTimeModule());
//
//        MovieRequestDto movieRequestDto = objectMapper.readValue(movieRequestDtoStr, MovieRequestDto.class);
//        movieRequestDto.setPoster(poster.getBytes());
//
//        // Manually validate the DTO
//        Set<ConstraintViolation<MovieRequestDto>> violations = validator.validate(movieRequestDto);
//        if (!violations.isEmpty()) {
//            String errorMessage = violations.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .collect(Collectors.joining(", "));
//            throw new ValidationException(errorMessage);
//        }
//
//        MovieResponseDto movie = movieService.createMovie(movieRequestDto);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(movie.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(movie);
//    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(){
        List<MovieResponseDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

//    @PutMapping
//    public ResponseEntity<?> editMovie(@Valid @RequestBody MovieEditRequestDto movieRequestDto, @RequestParam("poster") MultipartFile poster) throws IOException {
//        movieRequestDto.setPoster(poster.getBytes());
//        MovieResponseDto updatedMovie = movieService.editMovie(movieRequestDto);
//        return ResponseEntity.ok(updatedMovie);
//    }

//    @PutMapping
//    public ResponseEntity<?> editMovie(@Valid @RequestParam("movie") String movieEditRequestDtoStr, @RequestParam("poster") MultipartFile poster) throws IOException, MethodArgumentNotValidException {
//        objectMapper.registerModule(new JavaTimeModule());
//
//        MovieEditRequestDto movieEditRequestDto = objectMapper.readValue(movieEditRequestDtoStr, MovieEditRequestDto.class);
////        movieEditRequestDto.setPoster(poster.getBytes());
//
//        // Manually validate the DTO
//        Set<ConstraintViolation<MovieEditRequestDto>> violations = validator.validate(movieEditRequestDto);
//        if (!violations.isEmpty()) {
//            String errorMessage = violations.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .collect(Collectors.joining(", "));
//            throw new ValidationException(errorMessage);
//        }
//
//        MovieResponseDto updatedMovie = movieService.editMovie(movieEditRequestDto);
//        return ResponseEntity.ok(updatedMovie);
//    }

    @PutMapping
    public ResponseEntity<?> editMovie(@Valid @RequestBody MovieEditRequestDto movieEditRequestDto) {
        MovieResponseDto updatedMovie = movieService.editMovie(movieEditRequestDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponseDto> addPosterToMovie(@PathVariable Long id, @Valid @RequestBody Long posterId){
        MovieResponseDto movie = movieService.addPosterToMovie(id, posterId);
        return ResponseEntity.ok(movie);
    }

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
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/{movieId}/discussions/{discussionId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByDiscussionId(@PathVariable Long movieId, @PathVariable Long discussionId){
        List<CommentResponseDto> comments = commentService.getAllCommentsByDiscussionId(movieId, discussionId);
        return ResponseEntity.ok(comments);
    }

}
