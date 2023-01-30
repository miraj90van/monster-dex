package com.assignment.demo.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PagingDto<T> {

    private long totalPages;
    private long totalCount;
    private long size;
    private List<T> list;

    public PagingDto(long totalCount, int size, List<T> list) {
        this.totalCount = totalCount;
        this.size = size;
        totalPages = size == 0 ? 1 : (int) Math.ceil((double) totalCount / (double) size);
        this.list = list;
    }
}
