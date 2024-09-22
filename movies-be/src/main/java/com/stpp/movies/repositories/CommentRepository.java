package com.stpp.movies.repositories;

import com.stpp.movies.entities.Comment;
import com.stpp.movies.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiscussionId(Long id);
}
