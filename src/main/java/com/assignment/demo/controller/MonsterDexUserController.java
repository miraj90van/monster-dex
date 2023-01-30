package com.assignment.demo.controller;

import com.assignment.demo.constans.ApiPath;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.request.MonsterDexUserCatchRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.helper.ResponseHelper;
import com.assignment.demo.security.auth.UserPrincipal;
import com.assignment.demo.service.monster.MonsterDexUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class MonsterDexUserController {

  private final MonsterDexUserService monsterDexUserService;

  @GetMapping(ApiPath.USER_MONSTER_DEX)
  public Mono<ResponseEntity<PagingDto<MonsterDexPagingResponse>>> getMonsterDex(
      Authentication authentication,
      @ModelAttribute MonsterDexPagingRequest request,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "DESC") Sort.Direction sort,
      @RequestParam(defaultValue = "id") String... properties) {

    final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    request.setUserId(principal.getId());

    return monsterDexUserService.findAllPageable(request, page, size, sort, properties)
        .map(ResponseHelper::getResponseData);
  }

  @PostMapping(ApiPath.USER_MONSTER_DEX_CAPTURE)
  public Mono<ResponseEntity<String>> insertMonsterDex(Authentication authentication ,
      @RequestBody MonsterDexUserCatchRequest request) {

    final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    return monsterDexUserService.insertMonsterDexUserCatch(request, principal.getId())
        .map(ResponseHelper::getResponseMessage);
  }

}