package com.assignment.demo.entity.parameter;

import com.assignment.demo.entity.model.view.VwMonsterDexUser;
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
public class VwMonsterDexUserParam implements Serializable {
  private List<VwMonsterDexUser> vwMonsterDexUsers;
  private long count;
}