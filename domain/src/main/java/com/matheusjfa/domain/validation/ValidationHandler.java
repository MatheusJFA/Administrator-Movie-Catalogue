package com.matheusjfa.domain.validation;

import java.util.List;

public interface ValidationHandler {
	ValidationHandler append(Failure failure);
	ValidationHandler append(ValidationHandler handler);
	ValidationHandler validate(Validation validation);

	List<Failure> getFailures();

	default boolean hasError() {
		return getFailures() != null && !getFailures().isEmpty();
	}
}
