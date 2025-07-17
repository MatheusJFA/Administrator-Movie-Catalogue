package com.matheusjfa.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN input) throws Exception;
}