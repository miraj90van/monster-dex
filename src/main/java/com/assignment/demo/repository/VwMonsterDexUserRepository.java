package com.assignment.demo.repository;

import com.assignment.demo.constans.fields.VwMonsterDexFields;
import com.assignment.demo.entity.model.view.VwMonsterDexUser;
import com.assignment.demo.entity.parameter.VwMonsterDexUserParam;
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
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VwMonsterDexUserRepository extends AbstractVwMonsterDex {

  private final R2dbcEntityTemplate template;

  public Mono<VwMonsterDexUserParam> findAllPageable(MonsterDexPagingRequest request, int page,
      int size, Direction direction, String... properties){

    final Sort sort = SortHelper.of(direction, properties);
    Criteria criteria = createCriteria(request);

    if(!ObjectUtils.isEmpty(request.getUserId())){
      criteria = criteria.and(Criteria.empty().and(VwMonsterDexFields.USER_ID).is(request.getUserId()).or(VwMonsterDexFields.USER_ID).isNull());
    }

    Query query = Query.query(criteria);
    final int offset = getOffset(page, size);
    final Flux<VwMonsterDexUser> viewCoinBalanceFlux = template.select(VwMonsterDexUser.class)
        .matching(query.offset(offset).limit(size).sort(sort))
        .all();

    final Mono<Long> countMono = template.select(VwMonsterDexUser.class)
        .matching(query).count();

    return viewCoinBalanceFlux.collectList()
        .zipWith(countMono)
        .map(objects -> getSearchParam(objects.getT1(), objects.getT2()));
  }

  private VwMonsterDexUserParam getSearchParam(List<VwMonsterDexUser> vwMonsterDexUsers, long count){
    return VwMonsterDexUserParam.builder()
        .vwMonsterDexUsers(vwMonsterDexUsers)
        .count(count)
        .build();
  }

}