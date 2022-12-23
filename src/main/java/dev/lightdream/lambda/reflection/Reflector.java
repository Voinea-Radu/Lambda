package dev.lightdream.lambda.reflection;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Reflector {

    private final List<Class<?>> classes;

    public Reflector(ReflectionMain main, String packageName) {
        this.classes = getClasses(main, packageName);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public Reflector(Class<?> clazz) {
        this.classes = Arrays.asList(clazz);
    }

    public List<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return classes
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public List<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();

        classes.forEach(aClass ->
                methods.addAll(Arrays
                        .stream(aClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(annotation))
                        .collect(Collectors.toList())
                )
        );

        return methods;
    }

    @SuppressWarnings("unchecked")
    public <T> List<Class<? extends T>> getClassesThatExtend(Class<T> clazz) {
        return classes
                .stream()
                .filter(clazz::isAssignableFrom)
                .map(aClass -> (Class<? extends T>) aClass)
                .collect(Collectors.toList());
    }

    private List<Class<?>> getClasses(ReflectionMain main, String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        URL resource = ClassLoader.getSystemClassLoader().getResource(packageName.replaceAll("[.]", "/"));
        if (resource == null) {
            return classes;
        }

        File directory = new File(resource.getFile());
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursively search for classes in subpackages
                        classes.addAll(getClasses(main, packageName + "." + file.getName()));
                    } else if (file.getName().endsWith(".class")) {
                        // Add the class to the list
                        classes.add(getClass(main, file.getName(), packageName));
                    }
                }
            }
        }

        return classes;
    }


    private Class<?> getClass(ReflectionMain main, String className, String packageName) {
        String fullClassName = packageName + "." + className.substring(0, className.lastIndexOf('.'));
        return main.getClass(fullClassName);
    }


}
