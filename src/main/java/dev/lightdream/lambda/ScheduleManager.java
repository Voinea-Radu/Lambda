package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Builder
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ScheduleManager {

    private static @Getter ScheduleManager instance;

    static {
        builder().build();
    }

    private final ScheduledExecutorService scheduledExecutor;
    private final ExecutorService threadExecutor;

    private @Default int schedulePoolSize = 1;
    private @Default int threadPoolSize = 1;

    public ScheduleManager(int schedulePoolSize, int threadPoolSize) {
        instance = this;

        this.schedulePoolSize = schedulePoolSize;
        this.threadPoolSize = threadPoolSize;

        scheduledExecutor = Executors.newScheduledThreadPool(schedulePoolSize);
        threadExecutor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public static void runTaskLater(@NotNull LambdaExecutor task, long delay) {
        instance().scheduledExecutor.schedule(new CancelableTimeTask() {
            @Override
            public void execute() {
                task.execute();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    @SuppressWarnings("unused")
    public static @NotNull CancelableTimeTask runTaskLaterAsync(@NotNull LambdaExecutor executor, long delay) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskLaterAsync(task, delay);

        return task;
    }

    public static void runTaskLaterAsync(@NotNull CancelableTimeTask task, long delay) {
        instance().scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    @SuppressWarnings("unused")
    public static @NotNull CancelableTimeTask runTaskTimer(@NotNull LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                executor.execute();
            }
        };

        instance().scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);

        return task;
    }

    public static void runTaskTimer(@NotNull CancelableTimeTask task, long timer) {
        instance().scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    @SuppressWarnings("unused")
    public static @NotNull CancelableTimeTask runTaskTimerAsync(@NotNull LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                runTaskAsync(executor);
            }
        };

        runTaskTimerAsync(task, timer);

        return task;
    }

    public static void runTaskTimerAsync(@NotNull CancelableTimeTask task, long timer) {
        instance().scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public static void runTaskAsync(@NotNull LambdaExecutor task) {
        instance().threadExecutor.execute(task::execute);
    }

}
