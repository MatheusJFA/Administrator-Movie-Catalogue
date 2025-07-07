package com.matheusjfa.domain.exceptions;

import com.matheusjfa.domain.validation.Failure;

import java.util.List;

public class DomainException extends NoStacktraceException {

	private final List<Failure> failureList;

	private DomainException(final String message, final List<Failure> failureList) {
		super(message);
		this.failureList = failureList;
	}

	public static DomainException with(final Failure failures) {
		return new DomainException(failures.message(), List.of(failures));
	}

	public static DomainException with(final List<Failure> failureList) {
		return new DomainException("", failureList);
	}

	public List<Failure> getFailures() {
		return failureList;
	}
}

