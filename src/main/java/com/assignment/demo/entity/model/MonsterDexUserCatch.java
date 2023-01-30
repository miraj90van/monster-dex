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
@Table("monster_dex_user_catch")
public class MonsterDexUserCatch extends BaseData {

    @Column("monster_dex_id")
    private long monsterDexId;

    @Column("user_id")
    private long userId;
}