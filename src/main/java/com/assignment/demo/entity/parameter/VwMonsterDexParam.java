package com.assignment.demo.entity.parameter;

import com.assignment.demo.entity.model.view.VwMonsterDex;
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
public class VwMonsterDexParam implements Serializable {
  private List<VwMonsterDex> vwMonsterDexList;
  private long count;
}