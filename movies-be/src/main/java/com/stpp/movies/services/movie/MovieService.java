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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    private static final MovieMapper MAPPER = MovieMapper.INSTANCE;

    public Optional<MovieResponseDto> getMovieById(@Valid Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(MAPPER::movieToResponseDto);
    }

    @Transactional
    public MovieResponseDto createMovie(@Valid MovieRequestDto movieRequestDto) {
        Movie movie = MAPPER.requestDtoToMovie(movieRequestDto);

        if (movieRequestDto.getPosterId() != null) {
            Poster poster = fileService.getPosterAsPoster(movieRequestDto.getPosterId());
            movie.setPoster(poster);
        }else {
            movie.setPoster(null);
        }

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

        Movie movie = movieRepository.findById(movieEditRequestDto.getId()).get();

        if (movieEditRequestDto.getPosterId() != null) {
            Poster poster = fileService.getPosterAsPoster(movieEditRequestDto.getPosterId());
            movie.setPoster(poster);
        } else{
            movie.setPoster(null);
        }

        MAPPER.movieEditRequestDtoToMovie(movieEditRequestDto, movie);

        movie = movieRepository.save(movie);
        return MAPPER.movieToResponseDto(movie);
    }

    @Transactional
    public void deleteMovieById(@Valid Long id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie with ID " + id + " not found");
        }
        movieRepository.deleteById(id);
    }

    public MovieResponseDto updateMoviePoster(Long id, byte[] bytes) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie with ID " + id + " not found");
        }

        Movie movie = movieRepository.findById(id).get();
//        movie.setPoster(bytes);
        movie = movieRepository.save(movie);
        return MAPPER.movieToResponseDto(movie);
    }

    @Transactional
    public MovieResponseDto addPosterToMovie(Long id, Long posterId) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie with ID " + id + " not found");
        }
        if (!fileService.existsById(posterId)) {
            throw new NotFoundException("Poster with ID " + posterId + " not found");
        }

        Movie movie = movieRepository.findById(id).get();
        Poster poster = Poster.builder()
                .id(posterId)
                .poster(fileService.getPoster(posterId).getPoster())
                .build();
        movie.setPoster(poster);
        movie = movieRepository.save(movie);
        return MAPPER.movieToResponseDto(movie);
    }
}
