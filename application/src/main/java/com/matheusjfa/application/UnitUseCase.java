package com.matheusjfa.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN input);
}
