package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;
import dev.lightdream.lambda.lambda.RunnableExecutor;

import java.util.TimerTask;

@SuppressWarnings("unused")
public class ScheduleUtils {

    public static void runTaskLater(LambdaExecutor task, long delay) {
        ScheduleManager.get().runTaskLater(task, delay);
    }

    public static TimerTask runTaskLaterAsync(LambdaExecutor task, long delay) {
        return ScheduleManager.get().runTaskLaterAsync(task, delay);
    }

    public static void runTaskLaterAsync(TimerTask task, long delay) {
        ScheduleManager.get().runTaskLaterAsync(task, delay);
    }

    public static void runTaskTimer(RunnableExecutor task, long timer) {
        ScheduleManager.get().runTaskTimer(task, timer);
    }

    public static TimerTask runTaskTimerAsync(LambdaExecutor task, long timer) {
        return ScheduleManager.get().runTaskTimerAsync(task, timer);
    }

    public static void runTaskTimerAsync(TimerTask task, long timer) {
        ScheduleManager.get().runTaskTimerAsync(task, timer);
    }

    public static void runTaskAsync(LambdaExecutor task) {
        ScheduleManager.get().runTaskAsync(task);
    }

}
