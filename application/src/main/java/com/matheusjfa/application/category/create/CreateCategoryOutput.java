package com.matheusjfa.application.category.create;

import com.matheusjfa.domain.category.Category;
import com.matheusjfa.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
