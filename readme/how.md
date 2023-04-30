### Lambda Executors

```java
public class ExecutorUse {
    public void use() {
        // Create one of each
        ArgLambdaExecutor<Integer> printTimesTen = arg -> {
            arg = arg * 10;
            System.out.println(arg);
        };

        ArgsLambdaExecutor<String, String> printMergeStrings = (arg1, arg2) -> {
            System.out.println(arg1 + arg2);
        };

        LambdaExecutor printHelloWorld = () -> {
            System.out.println("Hello World");
        };

        ReturnArgLambdaExecutor<Integer, Integer> timesTen = arg -> {
            arg = arg * 10;
            return arg;
        };

        ReturnArgsLambdaExecutor<String, String, String> mergeStrings = (arg1, arg2) ->
                (arg1 + arg2);

        ReturnLambdaExecutor<String> hellowWorld = () -> "Hello World";

        // Call each
        printTimesTen.execute(5);
        printMergeStrings.execute("Hello ", "World");
        printHelloWorld.execute();

        System.out.println(timesTen.execute(5));
        System.out.println(mergeStrings.execute("Hello ", "World"));
        System.out.println(hellowWorld.execute());
    }
}
```

### Schedule

```java
public class ScheduleUtilsUse {
    public void use2() {
        // Run a timer with a delay of 0ms and a period of 1000ms
        // The argument timer for the executor is optional
        ScheduleUtils.runTaskTimerAsync(task -> {
            System.out.println("Print async every 1s");
            task.cancel();
        }, 0, 1000L);

        ScheduleUtils.runTaskTimer(task -> {
            System.out.println("Print every 1s");
            task.cancel();
        }, 0, 1000L);

        ScheduleUtils.runTaskLater(() -> System.out.println("Print after 1s"), 1000L);

        ScheduleUtils.runTaskAsync(() -> System.out.println("Print async"));
    }
}
```