package com.assignment.demo.entity.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonsterDexPagingResponse implements Serializable {
  private long id;
  private long userId;
  private String name;
  private String subName;
  private double height;
  private double weight;
  private String image;
  private String description;
  private String type;
  private String statMap;
  private int captured;
}