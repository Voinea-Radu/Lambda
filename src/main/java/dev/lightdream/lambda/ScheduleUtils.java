package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;

@SuppressWarnings("unused")
public class ScheduleUtils {

    public static void runTaskLater(LambdaExecutor task, long delay) {
        ScheduleManager.get().runTaskLater(task, delay);
    }

    public static CancelableTimeTask runTaskLaterAsync(LambdaExecutor task, long delay) {
        return ScheduleManager.get().runTaskLaterAsync(task, delay);
    }

    public static void runTaskLaterAsync(CancelableTimeTask task, long delay) {
        ScheduleManager.get().runTaskLaterAsync(task, delay);
    }

    public static CancelableTimeTask runTaskTimer(LambdaExecutor task, long timer) {
        return ScheduleManager.get().runTaskTimer(task, timer);
    }

    public static void runTaskTimer(CancelableTimeTask task, long timer) {
        ScheduleManager.get().runTaskTimer(task, timer);
    }

    public static CancelableTimeTask runTaskTimerAsync(LambdaExecutor task, long timer) {
        return ScheduleManager.get().runTaskTimerAsync(task, timer);
    }

    public static void runTaskTimerAsync(CancelableTimeTask task, long timer) {
        ScheduleManager.get().runTaskTimerAsync(task, timer);
    }

    public static void runTaskAsync(LambdaExecutor task) {
        ScheduleManager.get().runTaskAsync(task);
    }

}
