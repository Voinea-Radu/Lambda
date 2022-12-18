package dev.lightdream.lambda.lambda;

@SuppressWarnings("unused")
public interface ArgsLambdaExecutor<A, B> {
    void execute(A a, B b);
}