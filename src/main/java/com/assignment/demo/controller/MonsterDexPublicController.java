package com.assignment.demo.controller;

import com.assignment.demo.constans.ApiPath;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.helper.ResponseHelper;
import com.assignment.demo.service.monster.MonsterDexPublicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonsterDexPublicController {

  private final MonsterDexPublicService monsterDexPublicService;

  @GetMapping(ApiPath.PUBLIC_MONSTER_DEX)
  public Mono<ResponseEntity<PagingDto<MonsterDexPagingResponse>>> getMonsterDex(
      @ModelAttribute MonsterDexPagingRequest request,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "DESC") Sort.Direction sort,
      @RequestParam(defaultValue = "id") String... properties
  ) {

    return monsterDexPublicService.findAllPageable(request, page, size, sort, properties)
        .map(ResponseHelper::getResponseData);
  }

}