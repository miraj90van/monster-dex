package com.assignment.demo.repository;

import com.assignment.demo.entity.model.MonsterDexUserCatch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterDexUserCatchRepository extends ReactiveCrudRepository<MonsterDexUserCatch, Long> {
}
