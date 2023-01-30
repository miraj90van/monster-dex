package com.assignment.demo.repository;

import com.assignment.demo.entity.model.MonsterType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterTypeRepository extends ReactiveCrudRepository<MonsterType, Long> {
}
