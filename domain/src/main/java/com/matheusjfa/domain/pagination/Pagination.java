package com.matheusjfa.domain.pagination;

public record Pagination<T>(
        int currentPage,
        int perPage,
        long total,
        Iterable<T> items) {
}
