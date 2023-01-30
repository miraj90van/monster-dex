package com.assignment.demo.service.monster.paging.impl;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.view.VwMonsterDex;
import com.assignment.demo.entity.parameter.VwMonsterDexParam;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.exceptions.BusinessLogicException;
import com.assignment.demo.repository.VwMonsterDexRepository;
import com.assignment.demo.service.monster.paging.VwMonsterDexService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class VwMonsterDexAdminServiceImpl implements VwMonsterDexService {

  private final VwMonsterDexRepository vwMonsterDexRepository;

  @Override
  public boolean criteria(RoleType roleType) {
    return roleType.equals(RoleType.ROLE_ADMIN);
  }

  @Override
  public Mono<PagingDto<MonsterDexPagingResponse>> findAllPageable(MonsterDexPagingRequest request,
      int page, int size, Sort.Direction direction, String... properties){

    return vwMonsterDexRepository.findAllPageable(request, page, size, direction, properties)
        .map(vwMonsterDexParam -> buildResponsePaging(vwMonsterDexParam, size))
        .onErrorResume(throwable -> {
          log.error("error when process get Monster Dex : {}", throwable.getMessage());
          throw new BusinessLogicException(ResponseType.INVALID);
        });
  }

  private PagingDto<MonsterDexPagingResponse> buildResponsePaging(VwMonsterDexParam param, int size){
    final List<MonsterDexPagingResponse> responses = geMonsterDexPagingResponses(param);
    return new PagingDto<>(param.getCount(), size, responses);
  }

  private List<MonsterDexPagingResponse> geMonsterDexPagingResponses(VwMonsterDexParam  param){
    return param.getVwMonsterDexList().stream().map(this::getMonsterDexPagingResponse)
        .collect(Collectors.toList());
  }

  private MonsterDexPagingResponse getMonsterDexPagingResponse(VwMonsterDex monsterDex){
    return MonsterDexPagingResponse.builder()
        .id(monsterDex.getId())
        .name(monsterDex.getName())
        .subName(monsterDex.getSubName())
        .height(monsterDex.getHeight())
        .weight(monsterDex.getWeight())
        .type(monsterDex.getType())
        .statMap(monsterDex.getStatMap())
        .image(monsterDex.getImage())
        .description(monsterDex.getDescription())
        .build();
  }
}
