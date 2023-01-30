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
@Table("monster_type")
public class MonsterType extends BaseData{

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("enabled")
    private boolean enabled;
}
