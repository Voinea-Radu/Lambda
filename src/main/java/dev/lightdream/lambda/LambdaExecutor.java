package dev.lightdream.lambda;

@SuppressWarnings("unused")
@Deprecated
public abstract class LambdaExecutor {

    @Deprecated
    public interface ReturnLambdaExecutor<R, A> {
        R execute(A o);
    }

    @Deprecated
    public interface NoReturnLambdaExecutor<A> {
        void execute(A o);
    }

    @Deprecated
    public interface NoArgLambdaExecutor<R> {
        R execute();
    }

    @Deprecated
    public interface NoReturnNoArgLambdaExecutor {
        void execute();
    }

}


