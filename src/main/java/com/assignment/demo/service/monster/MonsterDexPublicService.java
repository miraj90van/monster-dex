package com.assignment.demo.service.monster;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.service.monster.paging.VwMonsterDexFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonsterDexPublicService {

  private final VwMonsterDexFactory vwMonsterDexFactory;

  public Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> findAllPageable(MonsterDexPagingRequest request,
      int page, int size, Sort.Direction direction, String... properties){

    return vwMonsterDexFactory.execute(request, RoleType.ROLE_PUBLIC, page, size, direction, properties)
        .map(response -> new BaseResponse<>(ResponseType.SUCCESS, response))
        .onErrorResume(throwable -> {
          log.error("error when process get Monster Dex : {}", throwable.getMessage());
          return Mono.just(new BaseResponse<>(ResponseType.INVALID));
        });
  }
}
