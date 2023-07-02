# Lambda

![Build](../../actions/workflows/build.yml/badge.svg)
![Version](https://img.shields.io/badge/Version-4.1.21-red.svg)

# Table Of Contents

1. [Description](#description)
2. [How to add to your project](#how-to-add-to-your-project)
3. [How to use](#how-to-use)

## Description

A simple lambda library used by many others proprietary libs and projects. Also implements a scheduler util class.
Allows to pass functions or methods as arguments to other methods

## How to add to your project

The artifact can be found at the repository https://repo.lightdream.dev or https://jitpack.io (under
com.github.L1ghtDream instead of dev.lightdream)

### Maven

```xml

<repositories>
    <repository>
        <id>lightdream-repo</id>
        <url>https://repo.lightdream.dev/</url>
    </repository>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupId>dev.lightdream</groupId>
        <artifactId>lambda</artifactId>
        <version>4.1.21</version>
    </dependency>
    <dependency>
        <groupId>com.github.L1ghtDream</groupId>
        <artifactId>lambda</artifactId>
        <version>4.1.21</version>
    </dependency>
</dependencies>
```

### Gradle - Groovy DSL

```groovy
repositories {
    maven { url "https://repo.lightdream.dev/" }
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "dev.lightdream:lambda:4.1.21"
    implementation "com.github.L1ghtDream:lambda:4.1.21"
}
```

### Gradle - Kotlin DSL

```kotlin
repositories {
    maven("https://repo.lightdream.dev/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.lightdream:lambda:4.1.21")
    implementation("com.github.L1ghtDream:lambda:4.1.21")
}
```

If you want to use an older version that is not available in https://repo.lightdream.dev you can try
using https://archive-repo.lightdream.dev

## How to use

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
