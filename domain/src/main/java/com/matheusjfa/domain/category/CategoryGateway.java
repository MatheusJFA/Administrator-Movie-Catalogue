package com.matheusjfa.domain.category;

import com.matheusjfa.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    Category update(Category category);

    void deleteById(String id);

    Optional<Category> findById(String id);

    Pagination<Category> findAll(CategorySearchQuery query);
}
