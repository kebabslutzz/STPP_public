package com.stpp.movies.services.movie;

import com.stpp.movies.dto.movie.MovieEditRequestDto;
import com.stpp.movies.dto.movie.MovieRequestDto;
import com.stpp.movies.dto.movie.MovieResponseDto;
import com.stpp.movies.entities.Movie;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.MovieRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class MovieService {

    private final MovieRepository movieRepository;

    private static final MovieMapper MAPPER = MovieMapper.INSTANCE;

    public Optional<MovieResponseDto> getMovieById(@Valid Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(MAPPER::movieToResponseDto);
    }

    @Transactional
    public MovieResponseDto createMovie(@Valid MovieRequestDto movieRequestDto) {
        Movie movie = MAPPER.requestDtoToMovie(movieRequestDto);
        movie = movieRepository.save(movie);
        return MAPPER.movieToResponseDto(movie);
    }

    @Transactional
    public List<MovieResponseDto> getAllMovies() {
        return movieRepository
                .findAllByOrderByReleaseDateAsc()
                .stream()
                .map(MAPPER::movieToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovieResponseDto editMovie(@Valid MovieEditRequestDto movieEditRequestDto) {
        if (!movieRepository.existsById(movieEditRequestDto.getId())) {
            throw new NotFoundException("Movie with ID " + movieEditRequestDto.getId() + " not found");
        }
//        Movie movie = movieRepository.findById(movieEditRequestDto.getId())
//                .orElseThrow(() -> new NoSuchElementException("Movie with ID " + movieEditRequestDto.getId() + " not found"));
        Movie movie = movieRepository.findById(movieEditRequestDto.getId()).get();

        MAPPER.movieEditRequestDtoToMovie(movieEditRequestDto, movie);

        movie = movieRepository.save(movie);
        return MAPPER.movieToResponseDto(movie);
    }

    @Transactional
    public void deleteMovieById(@Valid Long id) {
        movieRepository.findById(id)
                .ifPresentOrElse(
                        movie -> movieRepository.deleteById(id),
                        () -> {
                            throw new NotFoundException("Movie with ID " + id + " not found");
                        }
                );
    }
}
