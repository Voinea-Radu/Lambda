package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.ArgLambdaExecutor;
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
    public static Timer runTaskTimer(ArgLambdaExecutor<Timer> task, long delay, long timer) {
        Timer timerObject = new Timer();
        timerObject.schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute(timerObject);
            }
        }, delay, timer);

        return timerObject;
    }

    /**
     * Schedules a task to be executed after a delay repeatedly on a timer
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     * @param timer The timer in milliseconds
     * @return The timer object
     */
    public static Timer runTaskTimer(LambdaExecutor task, long delay, long timer) {
        Timer timerObject = new Timer();
        timerObject.schedule(new TimerTask() {
            @Override
            public void run() {
                task.execute();
            }
        }, delay, timer);

        return timerObject;
    }

    /**
     * Schedules a task to be executed after a delay repeatedly on a timer asynchronously
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     * @param timer The timer in milliseconds
     */
    public static Timer runTaskTimerAsync(ArgLambdaExecutor<Timer> task, long delay, long timer) {
        Timer timerObject = new Timer();

        timerObject.schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(() -> task.execute(timerObject));
            }
        }, delay, timer);

        return timerObject;
    }

    /**
     * Schedules a task to be executed after a delay repeatedly on a timer asynchronously
     *
     * @param task  The task to be executed
     * @param delay The delay in milliseconds
     * @param timer The timer in milliseconds
     */
    public static Timer runTaskTimerAsync(LambdaExecutor task, long delay, long timer) {
        Timer timerObject = new Timer();

        timerObject.schedule(new TimerTask() {
            @Override
            public void run() {
                runTaskAsync(task);
            }
        }, delay, timer);

        return timerObject;
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
