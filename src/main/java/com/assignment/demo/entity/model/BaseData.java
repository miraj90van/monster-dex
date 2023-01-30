package com.assignment.demo.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseData implements Serializable {

    @Id
    @Column("id")
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("created_date")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("updated_date")
    private LocalDateTime updatedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("deleted_date")
    private LocalDateTime deletedDate;

    @Column("created_at")
    private long createdAt;

    @Column("updated_at")
    private long updatedAt;

    @Column("deleted_at")
    private long deletedAt;
}
