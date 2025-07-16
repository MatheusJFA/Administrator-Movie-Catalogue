package com.matheusjfa.domain.validation;

public abstract class Validator {
    private final ValidationHandler handler;

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }

    protected ValidationHandler getHandler() {
        return this.handler;
    }

    protected abstract void validate() throws Exception;
}
