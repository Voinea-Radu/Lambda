package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        this.scheduledExecutor.schedule(new CancelableTimeTask() {
            @Override
            public void execute() {
                task.execute();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public CancelableTimeTask runTaskLaterAsync(LambdaExecutor executor, long delay) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskLaterAsync(task, delay);

        return task;
    }

    public void runTaskLaterAsync(CancelableTimeTask task, long delay) {
        this.scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public CancelableTimeTask runTaskTimer(LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                executor.execute();
            }
        };

        this.scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);

        return task;
    }

    public void runTaskTimer(CancelableTimeTask task, long timer) {
        this.scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public CancelableTimeTask  runTaskTimerAsync(LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskTimerAsync(task, timer);

        return task;
    }

    public void runTaskTimerAsync(CancelableTimeTask task, long timer) {
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
