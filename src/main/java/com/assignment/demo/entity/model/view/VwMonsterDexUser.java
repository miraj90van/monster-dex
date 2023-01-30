package com.assignment.demo.entity.model.view;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table("vw_monster_dex_user")
public class VwMonsterDexUser implements Serializable {

  @Id
  @Column("id")
  private long id;

  @Column("user_id")
  private long userId;

  @Column("name")
  private String name;

  @Column("sub_name")
  private String subName;

  @Column("height")
  private double height;

  @Column("weight")
  private double weight;

  @Column("image")
  private String image;

  @Column("description")
  private String description;

  @Column("type")
  private String type;

  @Column("stat_map")
  private String statMap;

  @Column("captured")
  private int captured;

}
