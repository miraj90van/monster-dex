package com.assignment.demo.repository;

import com.assignment.demo.constans.fields.VwMonsterDexFields;
import com.assignment.demo.entity.request.MonsterDexPagingRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.util.ObjectUtils;

public class AbstractVwMonsterDex {

  protected Criteria createCriteria(MonsterDexPagingRequest request){

    final String percentage = "%";
    Criteria criteria = Criteria.where(VwMonsterDexFields.ID).isNotNull();

    if(!ObjectUtils.isEmpty(request.getName())){
      criteria = criteria.and(VwMonsterDexFields.NAME).like(percentage
          .concat(request.getName())
          .concat(percentage)
      );
    }

    if(!ObjectUtils.isEmpty(request.getSubName())){
      criteria = criteria.and(VwMonsterDexFields.SUB_NAME).like(percentage
          .concat(request.getSubName())
          .concat(percentage)
      );
    }

    if(!ObjectUtils.isEmpty(request.getType())){
      criteria = criteria.and(VwMonsterDexFields.TYPE).like(percentage
          .concat(request.getType())
          .concat(percentage)
      );
    }

    if(!ObjectUtils.isEmpty(request.getHeight())){
      criteria = criteria.and(VwMonsterDexFields.HEIGHT).is(request.getHeight());
    }

    if(!ObjectUtils.isEmpty(request.getWeight())){
      criteria = criteria.and(VwMonsterDexFields.WEIGHT).is(request.getWeight());
    }

    return criteria;
  }

  protected int getOffset(int page, int size){
    if(page == 0){
      return 0;
    }
    return (page - 1) * size;
  }
}
