package com.assignment.demo.service.monster;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.model.MonsterDexUserCatch;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.request.MonsterDexUserCatchRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.helper.TimeHelper;
import com.assignment.demo.repository.MonsterDexUserCatchRepository;
import com.assignment.demo.service.monster.paging.VwMonsterDexFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonsterDexUserService {

  private final MonsterDexUserCatchRepository monsterDexUserCatchRepository;
  private final VwMonsterDexFactory vwMonsterDexFactory;

  public Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> findAllPageable(MonsterDexPagingRequest request,
      int page, int size, Sort.Direction direction, String... properties){

    return vwMonsterDexFactory.execute(request, RoleType.ROLE_USER, page, size, direction, properties)
        .map(response -> new BaseResponse<>(ResponseType.SUCCESS, response))
        .onErrorResume(throwable -> {
          log.error("error when process get Monster Dex : {}", throwable.getMessage());
          return Mono.just(new BaseResponse<>(ResponseType.INVALID));
        });
  }

  public Mono<BaseResponse<MonsterDexUserCatch>> insertMonsterDexUserCatch(MonsterDexUserCatchRequest request,
      long userId){

    final MonsterDexUserCatch monsterDexUserCatch = MonsterDexUserCatch.builder()
        .monsterDexId(request.getMonsterDexId())
        .userId(userId)
        .createdDate(TimeHelper.getLocalDateTimeNow())
        .createdAt(userId)
        .build();

    return monsterDexUserCatchRepository.save(monsterDexUserCatch)
        .map(dexUserCatch -> new BaseResponse<>(ResponseType.SUCCESS_CAPTURE_MONSTER, dexUserCatch))
        .onErrorResume(throwable -> {
          log.error("got error when insertMonsterDexUserCatch message: {}", throwable.getMessage(), throwable);
          return Mono.just(new BaseResponse<>(ResponseType.INVALID));
        });
  }

}