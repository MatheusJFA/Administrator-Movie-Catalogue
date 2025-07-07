package com.matheusjfa.domain.exceptions;

public class NoStacktraceException extends RuntimeException {
	public NoStacktraceException(final String message) {
		this(message, null);
	}

	public NoStacktraceException(final String message, final Throwable throwable) {
		super(message, throwable, true, false);
	}
}
