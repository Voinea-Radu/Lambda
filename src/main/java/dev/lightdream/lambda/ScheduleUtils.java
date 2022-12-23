package dev.lightdream.lambda;

import java.util.Timer;
import java.util.TimerTask;
import dev.lightdream.lambda.lambda.LambdaExecutor;

@SuppressWarnings("unused")
public class ScheduleUtils {

    public static void runTaskLater(LambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay);
    }

    public static void runTaskLaterAsync(LambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(task);
            }
        }, delay);
    }

    public static void runTaskTimer(LambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, timer);
    }

    public static void runTaskTimerAsync(LambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(task);
            }
        }, delay, timer);
    }

    public static void runTaskAsync(LambdaExecutor task) {
        new Thread(task::execute).start();
    }

    @Deprecated
    public static void runTaskLater(dev.lightdream.lambda.LambdaExecutor.NoReturnNoArgLambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay);
    }

    @Deprecated
    public static void runTaskTimer(dev.lightdream.lambda.LambdaExecutor.NoReturnNoArgLambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, timer);
    }

    @Deprecated

    public static void runTaskAsync(dev.lightdream.lambda.LambdaExecutor.NoReturnNoArgLambdaExecutor task) {
        new Thread(task::execute).start();
    }

}
