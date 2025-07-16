package com.matheusjfa.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(ErrorMessage error) throws Exception;

    ValidationHandler append(ValidationHandler handler) throws Exception;

    <T> T validate(Validation<T> validation) throws Exception;

    List<ErrorMessage> getErrors();

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }
}

