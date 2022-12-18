package dev.lightdream.lambda.lambda;

@SuppressWarnings("unused")
public interface ReturnArgsLambdaExecutor<R, A, B> {
    R execute(A a, B b);
}