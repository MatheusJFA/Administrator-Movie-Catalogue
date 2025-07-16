package com.matheusjfa.domain.category;

import com.matheusjfa.domain.AggregateRoot;
import com.matheusjfa.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {
    private final String name;
    private final String description;
    private final boolean isActive;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category create(String name, String description, boolean isActive) {
        final var id = CategoryID.generate();
        final var now = Instant.now();
        final var deleted = (isActive) ? null : now;

        return new Category(id, name, description, isActive, now, now, deleted);
    }

    @Override
    public void validate(ValidationHandler handler) throws Exception {
        final var validator = new CategoryValidator(this, handler);
        validator.validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
