package com.assignment.demo.repository;

import com.assignment.demo.entity.model.MonsterStat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterStatRepository extends ReactiveCrudRepository<MonsterStat, Long> {
}
