package dev.lightdream.lambda.reflection;

import dev.lightdream.logger.Debugger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static List<Method> getMethodsAnnotatedWith(String packageName, Class<? extends Annotation> annotation) {
        List<Method> output = new ArrayList<>();
        for (String clasName : new Reflections(packageName).getAll(Scanners.SubTypes)) {
            try {
                for (Method method : Class.forName(clasName).getMethods()) {
                    if (method.isAnnotationPresent(annotation)) {
                        output.add(method);
                    }
                }
            } catch (Throwable ignored) {
                Debugger.log("Failed to load class: " + clasName);
            }
        }

        return output;
    }

}
