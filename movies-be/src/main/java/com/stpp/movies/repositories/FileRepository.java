package com.stpp.movies.repositories;

import com.stpp.movies.entities.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Poster, Long> {
}
