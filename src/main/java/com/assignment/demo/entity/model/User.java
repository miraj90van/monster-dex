package com.assignment.demo.entity.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table("user")
public class User extends BaseData {

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("phone")
    private String phone;

    @Column("enabled")
    private boolean enabled;

    @Column("roles")
    private String roles;
}