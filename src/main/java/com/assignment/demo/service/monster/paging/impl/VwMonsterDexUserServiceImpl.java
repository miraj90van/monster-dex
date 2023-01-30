package com.assignment.demo.service.monster.paging.impl;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.view.VwMonsterDexUser;
import com.assignment.demo.entity.parameter.VwMonsterDexUserParam;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.exceptions.BusinessLogicException;
import com.assignment.demo.repository.VwMonsterDexUserRepository;
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
public class VwMonsterDexUserServiceImpl implements VwMonsterDexService {

  private final VwMonsterDexUserRepository vwMonsterDexUserRepository;

  @Override
  public boolean criteria(RoleType roleType) {
    return roleType.equals(RoleType.ROLE_USER);
  }

  @Override
  public Mono<PagingDto<MonsterDexPagingResponse>> findAllPageable(MonsterDexPagingRequest request,
      int page, int size, Sort.Direction direction, String... properties){

    return vwMonsterDexUserRepository.findAllPageable(request, page, size, direction, properties)
        .map(vwMonsterDexParam -> buildResponsePaging(vwMonsterDexParam, size))
        .onErrorResume(throwable -> {
          log.error("error when process get Monster Dex : {}", throwable.getMessage());
          throw new BusinessLogicException(ResponseType.INVALID);
        });
  }

  private PagingDto<MonsterDexPagingResponse> buildResponsePaging(VwMonsterDexUserParam param, int size){
    final List<MonsterDexPagingResponse> responses = geMonsterDexPagingResponses(param);
    return new PagingDto<>(param.getCount(), size, responses);
  }

  private List<MonsterDexPagingResponse> geMonsterDexPagingResponses(VwMonsterDexUserParam  param){
    return param.getVwMonsterDexUsers().stream().map(this::getMonsterDexPagingResponse)
        .collect(Collectors.toList());
  }

  private MonsterDexPagingResponse getMonsterDexPagingResponse(VwMonsterDexUser vwMonsterDexUser){
    return MonsterDexPagingResponse.builder()
        .id(vwMonsterDexUser.getId())
        .userId(vwMonsterDexUser.getUserId())
        .name(vwMonsterDexUser.getName())
        .subName(vwMonsterDexUser.getSubName())
        .height(vwMonsterDexUser.getHeight())
        .weight(vwMonsterDexUser.getWeight())
        .type(vwMonsterDexUser.getType())
        .statMap(vwMonsterDexUser.getStatMap())
        .image(vwMonsterDexUser.getImage())
        .description(vwMonsterDexUser.getDescription())
        .captured(vwMonsterDexUser.getCaptured())
        .build();
  }

}