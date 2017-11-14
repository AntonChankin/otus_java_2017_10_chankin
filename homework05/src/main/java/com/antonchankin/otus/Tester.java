package com.antonchankin.otus;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Tester {

    public boolean runTests(String name) {
        boolean isSuccessful = false;
        //TODO: Method detecting is name is class or package
        return isSuccessful;
    }

    public boolean runTestsByClass(String name) {
        boolean isSuccessful = false;
        //TODO: Method running tests for class
        return isSuccessful;
    }

    public boolean runTestsByPackage(String name) {
        boolean isSuccessful = false;
        //TODO: Method running tests for package
        return isSuccessful;
    }

    static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
