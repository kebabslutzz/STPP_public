package com.stpp.movies.controllers;

import com.stpp.movies.dto.MovieEditRequestDto;
import com.stpp.movies.dto.MovieRequestDto;
import com.stpp.movies.dto.MovieResponseDto;
import com.stpp.movies.services.movie.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

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

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(){
        List<MovieResponseDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @PatchMapping
    public ResponseEntity<?> editMovie(@Valid @RequestBody MovieEditRequestDto movieRequestDto){
        try{
            MovieResponseDto updatedMovie = movieService.editMovie(movieRequestDto);
            return ResponseEntity.ok(updatedMovie);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
//        MovieResponseDto updatedMovie = movieService.editMovie(movieRequestDto);
//        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id){
        try{
            movieService.deleteMovieById(id);
            return ResponseEntity.noContent().build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
//        movieService.deleteMovieById(id);
//        return ResponseEntity.noContent().build();
    }

}
