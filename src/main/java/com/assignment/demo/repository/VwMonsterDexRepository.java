package com.assignment.demo.repository;

import com.assignment.demo.entity.model.view.VwMonsterDex;
import com.assignment.demo.entity.parameter.VwMonsterDexParam;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import com.assignment.demo.helper.SortHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VwMonsterDexRepository extends AbstractVwMonsterDex {

    private final R2dbcEntityTemplate template;

    public Mono<VwMonsterDexParam> findAllPageable(MonsterDexPagingRequest request, int page,
        int size, Direction direction, String... properties){

        final Sort sort = SortHelper.of(direction, properties);
        final Criteria criteria = createCriteria(request);

        Query query = Query.query(criteria);
        final int offset = getOffset(page, size);
        final Flux<VwMonsterDex> viewCoinBalanceFlux = template.select(VwMonsterDex.class)
            .matching(query.offset(offset).limit(size).sort(sort))
            .all();

        final Mono<Long> countMono = template.select(VwMonsterDex.class)
            .matching(query).count();

        return viewCoinBalanceFlux.collectList()
            .zipWith(countMono)
            .map(objects -> getSearchParam(objects.getT1(), objects.getT2()));
    }

    private VwMonsterDexParam getSearchParam(List<VwMonsterDex> monsterDexList, long count){
        return VwMonsterDexParam.builder()
            .vwMonsterDexList(monsterDexList)
            .count(count)
            .build();
    }

}