package dev.lightdream.lambda.reflection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Reflector {

    private final List<Class<?>> classes;

    public Reflector(String packageName) {
        this.classes = getClasses(packageName);
    }

    public List<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return classes
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<Class<? extends T>> getClassesThatExtend(Class<T> clazz) {
        return classes
                .stream()
                .filter(clazz::isAssignableFrom)
                .map(aClass -> (Class<? extends T>) aClass)
                .collect(Collectors.toList());
    }

    private List<Class<?>> getClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null) {
            return new ArrayList<>();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toList());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }


}
