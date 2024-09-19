package com.stpp.movies.repositories;

import com.stpp.movies.entities.Discussion;
import com.stpp.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByOrderByReleaseDateAsc();

    void deleteById(Long id);
}
