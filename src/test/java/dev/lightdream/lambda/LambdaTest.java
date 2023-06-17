package dev.lightdream.lambda;

import dev.lightdream.lambda.lambda.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaTest {

    @Test
    public void testLambdaExecutors() {
        ArgLambdaExecutor<Integer> printTimesTen = arg -> {
            arg = arg * 10;
            System.out.println(arg);
        };

        ArgsLambdaExecutor<String, String> printMergeStrings = (arg1, arg2) -> System.out.println(arg1 + arg2);

        LambdaExecutor printHelloWorld = () -> System.out.println("Hello World");

        ReturnArgLambdaExecutor<Integer, Integer> timesTen = arg -> {
            arg = arg * 10;
            return arg;
        };

        ReturnArgsLambdaExecutor<String, String, String> mergeStrings = (arg1, arg2) -> (arg1 + arg2);

        ReturnLambdaExecutor<String> hellowWorld = () -> "Hello World";

        assertDoesNotThrow(() -> printTimesTen.execute(10));
        assertDoesNotThrow(() -> printMergeStrings.execute("test", "test"));
        assertDoesNotThrow(printHelloWorld::execute);

        int result1 = timesTen.execute(10);
        String result2 = mergeStrings.execute("test", "test");
        String result3 = hellowWorld.execute();


        assertEquals(100, result1);
        assertEquals("testtest", result2);
        assertEquals("Hello World", result3);
    }

    @SneakyThrows
    @Test
    public void testRunTaskLater() {
        AtomicBoolean executed = new AtomicBoolean(false);

        ScheduleUtils.runTaskLater(() -> executed.set(true), 1000);

        Thread.sleep(1500);

        assertTrue(executed.get());
    }

    @SneakyThrows
    @Test
    public void testRunTaskTimer() {
        AtomicInteger executed = new AtomicInteger(0);

        ScheduleUtils.runTaskTimer(timer -> {
            executed.getAndAdd(1);

            if (executed.get() == 5) {
                timer.cancel();
            }
        }, 1000);

        Thread.sleep(6000);

        assertEquals(5, executed.get());
    }

}
