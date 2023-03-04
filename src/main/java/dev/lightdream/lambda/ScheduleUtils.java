package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.LambdaExecutor;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public class ScheduleUtils {

    /**
     * Schedules a task to be executed after a delay
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     */
    public static void runTaskLater(LambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay);
    }

    /**
     * Schedules a task to be executed after a delay asynchronously
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     */
    public static void runTaskLaterAsync(LambdaExecutor task, long delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(task);
            }
        }, delay);
    }

    /**
     * Schedules a task to be executed after a delay repeatedly on a timer
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     * @param timer The timer in milliseconds
     */
    public static void runTaskTimer(LambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, timer);
    }

    /**
     * Schedules a task to be executed after a delay repeatedly on a timer asynchronously
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     * @param timer The timer in milliseconds
     */
    public static void runTaskTimerAsync(LambdaExecutor task, long delay, long timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(task);
            }
        }, delay, timer);
    }

    /**
     * Runs a task asynchronously
     *
     * @param task The task to be executed
     */
    public static void runTaskAsync(LambdaExecutor task) {
        new Thread(task::execute).start();
    }
}
