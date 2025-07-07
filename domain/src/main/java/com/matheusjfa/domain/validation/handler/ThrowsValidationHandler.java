package com.matheusjfa.domain.validation.handler;

import com.matheusjfa.domain.exceptions.DomainException;
import com.matheusjfa.domain.validation.Failure;
import com.matheusjfa.domain.validation.Validation;
import com.matheusjfa.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
	@Override
	public ValidationHandler append(Failure failure) {
		throw DomainException.with(failure);
	}

	@Override
	public ValidationHandler append(ValidationHandler handler) {
		throw DomainException.with(handler.getFailures());
	}

	@Override
	public ValidationHandler validate(Validation validation) {
		try {
			validation.validate();
		} catch (final Exception e) {
			throw DomainException.with(new Failure(e.getMessage()));
		}

		return this;
	}

	@Override
	public List<Failure> getFailures() {
		return List.of();
	}
}
