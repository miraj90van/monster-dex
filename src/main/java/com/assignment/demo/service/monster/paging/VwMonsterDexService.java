package com.assignment.demo.service.monster.paging;

import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;

public interface VwMonsterDexService {

  boolean criteria(RoleType roleType);

  Mono<PagingDto<MonsterDexPagingResponse>> findAllPageable(MonsterDexPagingRequest request,
      int page, int size, Sort.Direction direction, String... properties);

}
