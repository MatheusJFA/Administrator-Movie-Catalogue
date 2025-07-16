package com.matheusjfa.domain.validation.handler;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.ErrorMessage;
import com.matheusjfa.domain.validation.Validation;
import com.matheusjfa.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<ErrorMessage> errors;

    private Notification(List<ErrorMessage> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<ErrorMessage>());
    }

    public static Notification create(final Throwable throwable) {
        return create(new ErrorMessage(throwable.getMessage()));
    }

    public static Notification create(ErrorMessage error) {
        return new Notification(new ArrayList<ErrorMessage>()).append(error);
    }

    @Override
    public Notification append(ErrorMessage error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public Notification append(ValidationHandler handler) throws Exception {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> validation) throws Exception {
        try {
            validation.validate();
        } catch (DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (Exception ex) {
            this.errors.add(new ErrorMessage(ex.getMessage()));
        }

        return null;
    }

    @Override
    public List<ErrorMessage> getErrors() {
        return errors;
    }
}
