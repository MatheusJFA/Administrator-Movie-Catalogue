package com.matheusjfa.application.category.update;

import com.matheusjfa.domain.category.Category;
import com.matheusjfa.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {
    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
