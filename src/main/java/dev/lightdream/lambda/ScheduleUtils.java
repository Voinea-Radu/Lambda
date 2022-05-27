package dev.lightdream.lambda;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduleUtils {

    public static void runTaskLater(LambdaExecutor.NoReturnNoArgLambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay);
    }

    public static void runTaskTimer(LambdaExecutor.NoReturnNoArgLambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, timer);
    }

}
