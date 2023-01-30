package com.assignment.demo.controller;


import com.assignment.demo.constans.ApiPath;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.request.MonsterDexRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.entity.response.MonsterDexResponse;
import com.assignment.demo.helper.ResponseHelper;
import com.assignment.demo.service.monster.MonsterDexAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MonsterDexAdminController {

    private final MonsterDexAdminService monsterDexAdminService;

    @GetMapping(ApiPath.ADMIN_MONSTER_DEX)
    public Mono<ResponseEntity<PagingDto<MonsterDexPagingResponse>>> getMonsterDex(
        @ModelAttribute MonsterDexPagingRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "DESC") Sort.Direction sort,
        @RequestParam(defaultValue = "id") String... properties
    ) {
        return monsterDexAdminService.getMonsterDex(request, page, size, sort, properties)
                .map(ResponseHelper::getResponseData);
    }

    @PostMapping(ApiPath.ADMIN_MONSTER_DEX)
    public Mono<ResponseEntity<MonsterDexResponse>> insertMonsterDex(@RequestBody MonsterDexRequest request) {
        return monsterDexAdminService.insertMonsterDex(request)
                .map(ResponseHelper::getResponseData);
    }

    @PutMapping(ApiPath.ADMIN_MONSTER_DEX+ "/{id}")
    public Mono<ResponseEntity<MonsterDexResponse>> updateMonsterDex(@RequestBody MonsterDexRequest request,
        @PathVariable long id) {
        return monsterDexAdminService.updateMonsterDex(request, id)
            .map(ResponseHelper::getResponseData);
    }

    @DeleteMapping(ApiPath.ADMIN_MONSTER_DEX+ "/{id}")
    public Mono<ResponseEntity<Boolean>> deleteMonsterDex(@PathVariable long id) {
        return monsterDexAdminService.deleteMonsterDex(id)
            .map(ResponseHelper::getResponseData)
            .onErrorResume(throwable -> {
                log.error("throwable message: {}", throwable.getMessage(), throwable);
                return Mono.error(throwable);
            });
    }
}