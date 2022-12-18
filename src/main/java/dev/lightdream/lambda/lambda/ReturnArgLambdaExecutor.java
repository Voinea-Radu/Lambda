package dev.lightdream.lambda.lambda;

@SuppressWarnings("unused")
public interface ReturnArgLambdaExecutor<R, A> {
    R execute(A a);
}