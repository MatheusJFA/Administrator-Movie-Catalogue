package com.matheusjfa.domain.category;

import com.matheusjfa.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    Category update(Category category);

    void deleteById(CategoryID id);

    Optional<Category> findById(CategoryID id);

    Pagination<Category> findAll(CategorySearchQuery query);
}
