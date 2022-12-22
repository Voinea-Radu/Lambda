package dev.lightdream.lambda.reflection;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Reflector {

    private final ReflectionMain main;
    private final List<Class<?>> classes;

    public Reflector(ReflectionMain main, String packageName) {
        this.main = main;
        this.classes = getClasses(packageName);
    }

    public List<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return classes
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(annotation))
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
                        classes.addAll(getClasses(packageName + "." + file.getName()));
                    } else if (file.getName().endsWith(".class")) {
                        // Add the class to the list
                        classes.add(getClass(file.getName(), packageName));
                    }
                }
            }
        }

        return classes;
    }


    private Class<?> getClass(String className, String packageName) {
        String fullClassName = packageName + "." + className.substring(0, className.lastIndexOf('.'));
        return main.getClass(fullClassName);
    }


}
