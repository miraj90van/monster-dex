package com.assignment.demo.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonsterDexPagingRequest implements Serializable {
  private long id;
  private String name;
  private String subName;
  private Double height;
  private Double weight;
  private String image;
  private String description;
  private String type;
  private String statMap;
  private Long userId;
}