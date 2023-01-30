package com.assignment.demo.repository;

import com.assignment.demo.entity.model.MonsterDex;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MonsterDexRepository extends ReactiveCrudRepository<MonsterDex, Long> {

  @Query(value = "DELETE FROM monster_dex where id = :id ")
  Mono<Void> deleteById(long id);
}
