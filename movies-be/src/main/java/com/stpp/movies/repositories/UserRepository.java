package com.stpp.movies.repositories;

import com.stpp.movies.entities.Discussion;
import com.stpp.movies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
}
