package com.assignment.demo.repository;

import com.assignment.demo.entity.model.MonsterDexType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MonsterDexTypeRepository extends ReactiveCrudRepository<MonsterDexType, Long> {

  @Query(value = "DELETE FROM monster_dex_type where monster_dex_id = :id ")
  Mono<Void> deleteByMonsterDexId(long id);
}
