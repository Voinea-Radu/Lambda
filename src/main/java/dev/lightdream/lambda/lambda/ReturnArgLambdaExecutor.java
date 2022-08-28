package dev.lightdream.lambda.lambda;

public interface ReturnArgLambdaExecutor<R, A> {
    R execute(A a);
}