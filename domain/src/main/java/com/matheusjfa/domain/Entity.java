package com.matheusjfa.domain;

import com.matheusjfa.domain.validation.ValidationHandler;

import java.util.Objects;

public abstract class Entity<ID extends Identifier> {
    private final ID id;

    protected Entity(ID id) {
        this.id = Objects.requireNonNull(id, "O Identificador não pode ser nulo");
    }

    public ID getId() {
        return id;
    }

    public abstract void validate(ValidationHandler handler) throws Exception;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
