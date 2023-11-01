package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import dev.lightdream.lambda.lambda.RunnableExecutor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.TimerTask;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public class ScheduleManager {

    private static ScheduleManager instance;

    private final ScheduledExecutorService scheduledExecutor;
    private final ExecutorService threadExecutor;

    public ScheduleManager(Builder builder) {
        this.scheduledExecutor = Executors.newScheduledThreadPool(builder.getSchedulePoolSize());
        this.threadExecutor = Executors.newFixedThreadPool(builder.getThreadPoolSize());
    }

    public static ScheduleManager get() {
        return ScheduleManager.instance;
    }

    public void setStatic() {
        ScheduleManager.instance = this;
    }

    public void runTaskLater(LambdaExecutor task, long delay) {
        this.scheduledExecutor.schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public TimerTask runTaskLaterAsync(LambdaExecutor executor, long delay) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(executor);
            }
        };

        runTaskLaterAsync(task, delay);

        return task;
    }

    public void runTaskLaterAsync(TimerTask task, long delay) {
        this.scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public void runTaskTimer(RunnableExecutor task, long timer) {
        ScheduledFuture<?> future = this.scheduledExecutor.scheduleAtFixedRate
                (task::internalExecute, 0, timer, TimeUnit.MILLISECONDS);
        task.setScheduledFuture(future);
    }

    public TimerTask runTaskTimerAsync(LambdaExecutor executor, long timer) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(executor);
            }
        };

        runTaskTimerAsync(task, timer);

        return task;
    }

    public void runTaskTimerAsync(TimerTask task, long timer) {
        this.scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public void runTaskAsync(LambdaExecutor task) {
        threadExecutor.execute(task::execute);
    }

    @NoArgsConstructor
    @Getter
    public static class Builder {

        private int schedulePoolSize = 1;
        private int threadPoolSize = 1;

        public Builder setSchedulePoolSize(int schedulePoolSize) {
            this.schedulePoolSize = schedulePoolSize;
            return this;
        }

        public Builder setThreadPoolSize(int threadPoolSize) {
            this.threadPoolSize = threadPoolSize;
            return this;
        }

        public ScheduleManager build() {
            return new ScheduleManager(this);
        }
    }
}
