package io.github.dudupuci.application.usecases.base;

public abstract class UseCase<Input ,Output> {
    public abstract Output execute(Input input);
}
