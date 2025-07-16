package com.matheusjfa.domain.exceptions;

import com.matheusjfa.domain.validation.ErrorMessage;

import java.util.List;

public class DomainException extends NoStackTraceException {
    private final List<ErrorMessage> errors;

    public DomainException(final String message, final List<ErrorMessage> errors) {
        super(message);
        this.errors = errors;
    }

    public static Exception with(final ErrorMessage errorMessage) {
        return new DomainException(errorMessage.message(), List.of(errorMessage));
    }

    public static Exception with(final List<ErrorMessage> errorMessages) {
        return new DomainException("", errorMessages);
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }
}

