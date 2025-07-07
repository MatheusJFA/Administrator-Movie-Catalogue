package com.matheusjfa.domain.validation;

public abstract class Validation {
	private final ValidationHandler handler;

	protected Validation(final ValidationHandler handler) {
		this.handler = handler;
	}

	public abstract void validate();

	protected ValidationHandler getHandler() {
		return this.handler;
	}
}
