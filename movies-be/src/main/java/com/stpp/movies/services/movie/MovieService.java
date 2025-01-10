package com.stpp.movies.services.movie;

import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.entities.Poster;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.MovieRepository;
import com.stpp.movies.services.file.FileService;
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
public class MovieService {

  private final MovieRepository movieRepository;
  private final FileService fileService;

  private static final MovieMapper MAPPER = MovieMapper.INSTANCE;

  public MovieResponseDto getMovieById(@Valid Long id) {
    return movieRepository.findById(id)
      .map(MAPPER::movieToResponseDto)
      .orElseThrow(() -> new NotFoundException("Movie with ID " + id + " not found"));
  }

  public MovieResponseDto createMovie(@Valid MovieRequestDto movieRequestDto) {
    Movie movie = MAPPER.requestDtoToMovie(movieRequestDto);

    if (movieRequestDto.getPosterId() != null) {
      Poster poster = fileService.getPosterAsPoster(movieRequestDto.getPosterId());
      movie.setPoster(poster);
    } else {
      movie.setPoster(null);
    }

    movie = movieRepository.save(movie);
    return MAPPER.movieToResponseDto(movie);
  }

  public List<MovieResponseDto> getAllMovies() {
    return movieRepository
      .findAllByOrderByReleaseDateAsc()
      .stream()
      .map(MAPPER::movieToResponseDto)
      .toList();
  }

  public MovieResponseDto editMovie(Long movieId, @Valid MovieEditRequestDto movieEditRequestDto) {
    Movie movie = movieRepository.findById(movieId)
      .orElseThrow(() -> new NotFoundException("Movie with ID " + movieId + " not found"));

    if (movieEditRequestDto.getPosterId() != null) {
      if (!fileService.existsById(movieEditRequestDto.getPosterId())) {
        throw new NotFoundException("Poster with ID " + movieEditRequestDto.getPosterId() + " not found");
      }
      Poster poster = fileService.getPosterAsPoster(movieEditRequestDto.getPosterId());
      movie.setPoster(poster);
    }
    
    MAPPER.movieEditRequestDtoToMovie(movieEditRequestDto, movie);

    movie = movieRepository.save(movie);
    return MAPPER.movieToResponseDto(movie);
  }

  public void deleteMovieById(@Valid Long id) {
    if (!movieRepository.existsById(id)) {
      throw new NotFoundException("Movie with ID " + id + " not found");
    }
    movieRepository.deleteById(id);
  }
}
