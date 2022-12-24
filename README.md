# Lambda

![Build](../../actions/workflows/build.yml/badge.svg)
![Version](https://img.shields.io/badge/Version-3.3.0-red.svg)

A library that allows the execution of delayed tasks as well as repeated tasks. It provides an API to create lambda
functions.

## Use

### Maven

```xml

<repositories>
    <repository>
        <id>lightdream-repo</id>
        <url>https://repo.lightdream.dev/</url>
    </repository>
    <!-- Other repositories -->
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupId>dev.lightdream</groupId>
        <artifactId>Lambda</artifactId>
        <version>3.3.0</version>
    </dependency>
    <!-- Other dependencies -->
</dependencies>
```

### Gradle

```groovy
repositories {
    maven { url "https://repo.lightdream.dev/" }

    // Other repositories
}

dependencies {
    implementation "dev.lightdream:Lambda:3.3.0"

    // Other dependencies
}
```

## Example

Can be found in the [source code](/src/main/java/dev/lightdream/lambda/example)
