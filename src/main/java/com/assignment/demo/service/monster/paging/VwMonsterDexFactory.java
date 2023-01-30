package com.assignment.demo.service.monster.paging;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.exceptions.BusinessLogicException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class VwMonsterDexFactory {

  private final List<VwMonsterDexService> vwMonsterDexServices;

  public Mono<PagingDto<MonsterDexPagingResponse>> execute(MonsterDexPagingRequest request,
      RoleType roleType, int page, int size, Direction direction, String... properties){

    return vwMonsterDexServices.stream()
        .filter(service -> service.criteria(roleType))
        .findFirst()
        .map(service -> service.findAllPageable(request, page, size, direction, properties))
        .orElseGet(() -> {
          log.error("[VwMonsterDexFactory] factory not found ");
          throw new BusinessLogicException(ResponseType.INVALID);
        });
  }

}