package com.assignment.demo.repository;

import com.assignment.demo.entity.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    @Query(value = "select * from user where username = :username")
    Mono<User> getUserByUsername(String username);
}
