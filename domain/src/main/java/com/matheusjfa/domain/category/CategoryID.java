package com.matheusjfa.domain.category;

import com.matheusjfa.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String value;

    private CategoryID(final String value) {
        this.value = Objects.requireNonNull(value, "O Identificador não pode ser nulo");
    }

    //    Factory Methods
    public static CategoryID generate() {
        return CategoryID.from(UUID.randomUUID());
    }

    public static CategoryID from(final String id) {
        return new CategoryID(id);
    }

    public static CategoryID from(final UUID id) {
        return new CategoryID(id.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryID that = (CategoryID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
