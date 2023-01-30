package com.assignment.demo.entity.response;

import com.assignment.demo.entity.model.MonsterDexStat;
import com.assignment.demo.entity.model.MonsterDexType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MonsterDexResponse implements Serializable {
    private String name;
    private String subName;
    private double height;
    private double weight;
    private String image;
    private String description;
    private List<MonsterDexStat> monsterDexStats;
    private List<MonsterDexType> monsterDexTypes;
}
