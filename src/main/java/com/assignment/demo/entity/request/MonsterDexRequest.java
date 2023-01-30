package com.assignment.demo.entity.request;

import com.assignment.demo.entity.model.MonsterDexStat;
import com.assignment.demo.entity.model.MonsterDexType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonsterDexRequest implements Serializable {
    private String name;
    private String subName;
    private double height;
    private double weight;
    private String image;
    private String description;
    private List<MonsterDexStat> monsterDexStats;
    private List<MonsterDexType> monsterDexTypes;
}
