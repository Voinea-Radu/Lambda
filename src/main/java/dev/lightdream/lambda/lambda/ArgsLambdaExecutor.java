package dev.lightdream.lambda.lambda;

public interface ArgsLambdaExecutor<A, B> {
    void execute(A a, B b);
}