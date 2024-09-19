package com.stpp.movies.repositories;

import com.stpp.movies.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
