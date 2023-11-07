package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@Builder(builderClassName = "_Builder", toBuilder = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ScheduleManager {


    private static ScheduleManager instance;

    private static ScheduledExecutorService scheduledExecutor;
    private static ExecutorService threadExecutor;

    static {
        builder().build();
    }

    @lombok.Builder.Default
    private int schedulePoolSize = 1;
    @lombok.Builder.Default
    private int threadPoolSize = 1;

    public static void runTaskLater(@NotNull LambdaExecutor task, long delay) {
        scheduledExecutor.schedule(new CancelableTimeTask() {
            @Override
            public void execute() {
                task.execute();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

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
        scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public static @NotNull CancelableTimeTask runTaskTimer(@NotNull LambdaExecutor executor, long timer) {
        CancelableTimeTask task = new CancelableTimeTask() {
            @Override
            public void execute() {
                executor.execute();
            }
        };

        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);

        return task;
    }

    public static void runTaskTimer(@NotNull CancelableTimeTask task, long timer) {
        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

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
        scheduledExecutor.scheduleAtFixedRate(task, 0, timer, TimeUnit.MILLISECONDS);
    }

    public static void runTaskAsync(@NotNull LambdaExecutor task) {
        threadExecutor.execute(task::execute);
    }

    public static Builder builder() {
        return new Builder();
    }

    public ScheduleManager init() {
        instance = this;

        ScheduleManager.scheduledExecutor = Executors.newScheduledThreadPool(instance.schedulePoolSize());
        ScheduleManager.threadExecutor = Executors.newFixedThreadPool(instance.threadPoolSize());

        return this;
    }

    @Getter
    @Setter
    @Accessors(fluent = true)
    @NoArgsConstructor
    public static class Builder extends _Builder {
        public ScheduleManager build() {
            return super.build().init();
        }
    }
}
