package com.assignment.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table("monster_dex")
public class MonsterDex extends BaseData {

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

    @Column("enabled")
    private boolean enabled;

}
