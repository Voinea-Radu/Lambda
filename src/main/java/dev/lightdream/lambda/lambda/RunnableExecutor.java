package dev.lightdream.lambda.lambda;

import java.util.concurrent.ScheduledFuture;

public abstract class RunnableExecutor implements LambdaExecutor {

    private ScheduledFuture<?> future;
    private boolean canceled;

    public void internalExecute() {
        if (!canceled) {
            return;
        }

        execute();
    }

    public abstract void execute();

    public void setScheduledFuture(ScheduledFuture<?> future) {
        this.future = future;
    }

    public void cancel() {
        if (future != null) {
            future.cancel(false);
        }
        this.canceled = true;
    }

}
