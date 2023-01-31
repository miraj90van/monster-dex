package com.assignment.demo.service.monster;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.constans.RoleType;
import com.assignment.demo.entity.dto.PagingDto;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.model.MonsterDex;
import com.assignment.demo.entity.model.MonsterDexStat;
import com.assignment.demo.entity.model.MonsterDexType;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.entity.request.MonsterDexRequest;
import com.assignment.demo.entity.response.MonsterDexPagingResponse;
import com.assignment.demo.entity.response.MonsterDexResponse;
import com.assignment.demo.repository.MonsterDexRepository;
import com.assignment.demo.repository.MonsterDexStatRepository;
import com.assignment.demo.repository.MonsterDexTypeRepository;
import com.assignment.demo.service.monster.paging.VwMonsterDexFactory;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonsterDexAdminService {

    private final VwMonsterDexFactory vwMonsterDexFactory;
    private final MonsterDexRepository monsterDexRepository;
    private final MonsterDexStatRepository monsterDexStatRepository;
    private final MonsterDexTypeRepository monsterDexTypeRepository;
    private final TransactionalOperator operator;

    /**
     * this method for get all monster-dex
     * @param request monsterDexPagingRequest for search field
     * @param page default value 0
     * @param size default value 10
     * @param direction default value DESC
     * @param properties field for descending or ascending
     * @return Mono<BaseResponse<PagingDto<VwMonsterDex>>>
     */
    public Mono<BaseResponse<PagingDto<MonsterDexPagingResponse>>> getMonsterDex(MonsterDexPagingRequest request,
        int page, int size, Sort.Direction direction,
        String... properties){

        return vwMonsterDexFactory.execute(request, RoleType.ROLE_ADMIN, page, size, direction, properties)
                .map(response -> new BaseResponse<>(ResponseType.SUCCESS, response))
                .onErrorResume(throwable -> {
                    log.error("error when process get Monster Dex : {}", throwable.getMessage());
                    return Mono.just(new BaseResponse<>(ResponseType.INVALID));
                });
    }

    /**
     * this method for insert monster-dex
     * @param request MonsterDexRequest
     * @return Mono<BaseResponse<MonsterDexResponse>>
     */
    public Mono<BaseResponse<MonsterDexResponse>> insertMonsterDex(MonsterDexRequest request){

        return executeInsertMonsterDex(request)
                .flatMap(monsterDex -> executeInsertMonsterDexStat(request.getMonsterDexStats(), monsterDex.getId())
                        .flatMap(monsterDexStat -> executeInsertMonsterDexType(request.getMonsterDexTypes(), monsterDex.getId())
                                .map(monsterDexType -> getMonsterDexResponse(monsterDex, monsterDexStat, monsterDexType))))
                .as(operator::transactional)
                .map(response -> new BaseResponse<>(ResponseType.SUCCESS, response))
                .onErrorResume(throwable -> {
                    log.error("error when process insert Monster Dex : {}", throwable.getMessage());
                    return Mono.just(new BaseResponse<>(ResponseType.INVALID));
                });
    }

    /**
     * this method for updated monster-dex
     * @param request MonsterDexRequest
     * @param id unique id
     * @return Mono<BaseResponse<MonsterDexResponse>>
     */
    public Mono<BaseResponse<MonsterDexResponse>> updateMonsterDex(MonsterDexRequest request, long id){
        return monsterDexRepository.findById(id)
            .map(monsterDex -> buildMonsterDexForUpdate(request, monsterDex))
            .flatMap(monsterDexRepository::save)
            .flatMap(monsterDex -> executeUpdateMonsterDexStat(request, id)
            .flatMap(monsterDexStats -> executeUpdateMonsterDexType(request, id)
            .map(monsterDexTypes -> getMonsterDexResponse(monsterDex, monsterDexStats, monsterDexTypes))))
            .as(operator::transactional)
            .map(response -> new BaseResponse<>(ResponseType.SUCCESS, response))
            .switchIfEmpty(Mono.defer(() -> Mono.just(new BaseResponse<>(ResponseType.INVALID))))
            .onErrorResume(throwable -> {
                log.error("error when process update Monster Dex : {}", throwable.getMessage());
                return Mono.just(new BaseResponse<>(ResponseType.INVALID));
            });
    }

    /**
     * this method for delete monster-dex
     * @param id unique id
     * @return Mono<BaseResponse<Boolean>>
     */
    public Mono<BaseResponse<Boolean>> deleteMonsterDex(long id){
        return deleteByMonsterDexId(id)
            .flatMap(aBoolean -> deleteByMonsterDexStat(id))
            .flatMap(aBoolean -> deleteByMonsterDexType(id))
            .as(operator::transactional)
            .map(response -> new BaseResponse<>(ResponseType.SUCCESS, true))
            .onErrorResume(throwable -> {
                log.error("error when process get Monster Dex : {}", throwable.getMessage());
                return Mono.just(new BaseResponse<>(ResponseType.INVALID));
            });
    }

    private Mono<List<MonsterDexStat>> executeUpdateMonsterDexStat(MonsterDexRequest request, long id){
        if(!CollectionUtils.isEmpty(request.getMonsterDexStats())){
            return deleteByMonsterDexStat(id)
                .flatMap(aBoolean -> executeInsertMonsterDexStat(request.getMonsterDexStats(), id));
        }
        return Mono.just(Collections.emptyList());
    }

    private Mono<List<MonsterDexType>> executeUpdateMonsterDexType(MonsterDexRequest request, long id){
        if(!CollectionUtils.isEmpty(request.getMonsterDexStats())){
            return deleteByMonsterDexType(id)
                .flatMap(aBoolean -> executeInsertMonsterDexType(request.getMonsterDexTypes(), id));
        }
        return Mono.just(Collections.emptyList());
    }

    private MonsterDex buildMonsterDexForUpdate(MonsterDexRequest request, MonsterDex monsterDexExist){
        monsterDexExist.setName(request.getName());
        monsterDexExist.setSubName(request.getSubName());
        monsterDexExist.setImage(request.getImage());
        monsterDexExist.setDescription(request.getDescription());
        monsterDexExist.setHeight(request.getHeight());
        monsterDexExist.setWeight(request.getWeight());
        return monsterDexExist;
    }

    private Mono<Boolean> deleteByMonsterDexId(long id){
        Mono<Boolean> result = Mono.just(true);
        return monsterDexRepository.deleteById(id)
            .then(result)
            .onErrorResume(throwable -> Mono.just(false));
    }

    private Mono<Boolean> deleteByMonsterDexStat(long id){
        Mono<Boolean> result = Mono.just(true);
        return monsterDexStatRepository.deleteByMonsterDexId(id)
            .then(result)
            .onErrorResume(throwable -> Mono.just(false));
    }

    private Mono<Boolean> deleteByMonsterDexType(long id){
        Mono<Boolean> result = Mono.just(true);
        return monsterDexTypeRepository.deleteByMonsterDexId(id)
            .then(result)
            .onErrorResume(throwable -> Mono.just(false));
    }

    private Mono<MonsterDex> executeInsertMonsterDex(MonsterDexRequest request){
        final MonsterDex monsterDex = MonsterDex.builder()
                .name(request.getName())
                .subName(request.getSubName())
                .height(request.getHeight())
                .weight(request.getWeight())
                .image(request.getImage())
                .description(request.getDescription())
                .enabled(true)
                .build();

        return monsterDexRepository.save(monsterDex);
    }

    private Mono<List<MonsterDexStat>> executeInsertMonsterDexStat(List<MonsterDexStat> monsterStats, long id){
        final List<MonsterDexStat> monsterDexStats = monsterStats.stream()
            .map(type -> buildMonsterDexStat(type, id))
            .collect(Collectors.toList());

        return monsterDexStatRepository.saveAll(monsterDexStats)
            .collectList();
    }

    private Mono<List<MonsterDexType>> executeInsertMonsterDexType(List<MonsterDexType> monsterTypes, long id){
        final List<MonsterDexType> monsterDexTypes = monsterTypes.stream()
            .map(type -> buildMonsterDexType(type, id))
            .collect(Collectors.toList());

        return monsterDexTypeRepository.saveAll(monsterDexTypes).collectList();
    }

    private MonsterDexResponse getMonsterDexResponse(MonsterDex monsterDex, List<MonsterDexStat> monsterDexStats,
        List<MonsterDexType> monsterDexTypes){

        return MonsterDexResponse.builder()
                .name(monsterDex.getName())
                .subName(monsterDex.getSubName())
                .height(monsterDex.getHeight())
                .weight(monsterDex.getWeight())
                .image(monsterDex.getImage())
                .description(monsterDex.getDescription())
                .monsterDexStats(monsterDexStats)
                .monsterDexTypes(monsterDexTypes)
                .build();
    }

    private MonsterDexStat buildMonsterDexStat(MonsterDexStat type, long id){
        return MonsterDexStat.builder()
                .monsterStatId(type.getMonsterStatId())
                .monsterDexId(id)
                .amount(type.getAmount())
                .build();
    }

    private MonsterDexType buildMonsterDexType(MonsterDexType type, long id){
        return MonsterDexType.builder()
                .monsterTypeId(type.getMonsterTypeId())
                .monsterDexId(id)
                .build();
    }

}