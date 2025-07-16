package com.matheusjfa.domain.validation.handler;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.ErrorMessage;
import com.matheusjfa.domain.validation.Validation;
import com.matheusjfa.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(ErrorMessage error) throws Exception {
        throw DomainException.with(error);
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) throws Exception {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public <T> T validate(Validation<T> validation) throws Exception {
        try {
            return validation.validate();
        } catch (final Exception e) {
            throw DomainException.with(new ErrorMessage(e.getMessage()));
        }
    }

    @Override
    public List<ErrorMessage> getErrors() {
        return List.of();
    }
}
