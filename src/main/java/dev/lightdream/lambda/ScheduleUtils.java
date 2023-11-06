package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class ScheduleUtils {

    private static ScheduledExecutorService scheduledExecutor;
    private static ExecutorService threadExecutor;

    public static void init(Builder builder) {
        ScheduleUtils.scheduledExecutor = Executors.newScheduledThreadPool(builder.getSchedulePoolSize());
        ScheduleUtils.threadExecutor = Executors.newFixedThreadPool(builder.getThreadPoolSize());
    }

    public static void runTaskLater(LambdaExecutor task, long delay) {
        scheduledExecutor.schedule(new CancelableTimeTask() {
            @Override
            public void execute() {
                task.execute();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public static CancelableTimeTask runTaskLaterAsync(LambdaExecutor executor, long delay) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskLaterAsync(task, delay);

        return task;
    }

    public static void runTaskLaterAsync(CancelableTimeTask task, long delay) {
        scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public static CancelableTimeTask runTaskTimer(LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                executor.execute();
            }
        };

        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);

        return task;
    }

    public static void runTaskTimer(CancelableTimeTask task, long timer) {
        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public static CancelableTimeTask runTaskTimerAsync(LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskTimerAsync(task, timer);

        return task;
    }

    public static void runTaskTimerAsync(CancelableTimeTask task, long timer) {
        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public static void runTaskAsync(LambdaExecutor task) {
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

        public void build() {
            ScheduleUtils.init(this);
        }
    }
}
