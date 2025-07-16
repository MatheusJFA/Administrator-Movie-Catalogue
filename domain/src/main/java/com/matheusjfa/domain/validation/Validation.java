package com.matheusjfa.domain.validation;

public interface Validation<T> {
    T validate() throws Exception;
}
