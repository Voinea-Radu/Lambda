package dev.lightdream.lambda;

@SuppressWarnings("unused")
public abstract class LambdaExecutor {

    @SuppressWarnings("unused")
    public static String getVersion() {
        return "LambdaExecutor 1.0.4\n";
    }

    public interface ReturnLambdaExecutor<R, A> {
        R execute(A o);
    }

    public interface NoReturnLambdaExecutor<A> {
        void execute(A o);
    }

    @SuppressWarnings("unused")
    public interface NoArgLambdaExecutor<R> {
        R execute();
    }

    @SuppressWarnings("unused")
    public interface NoReturnNoArgLambdaExecutor {
        void execute();
    }

    public static class LambdaCatch {
        public interface ReturnLambdaCatch<R> {
            static <R> R executeCatch(ReturnLambdaCatch<R> executor) {
                try {
                    return executor.execute();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return null;
            }

            static <R> R executeCatch(ReturnLambdaCatch<R> executor, LambdaExecutor.ReturnLambdaExecutor<R, Throwable> catchExecutor) {
                try {
                    return executor.execute();
                } catch (Throwable t) {
                    R obj = catchExecutor.execute(t);
                    if (obj != null) {
                        return obj;
                    }
                }
                return null;
            }

            R execute() throws Throwable;
        }

        public interface NoReturnLambdaCatch {
            static void executeCatch(NoReturnLambdaCatch executor) {
                try {
                    executor.execute();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            static void executeCatch(NoReturnLambdaCatch executor, LambdaExecutor.NoReturnLambdaExecutor<Throwable> catchExecutor) {
                try {
                    executor.execute();
                } catch (Throwable t) {
                    catchExecutor.execute(t);
                }
            }

            void execute() throws Throwable;
        }
    }

}


