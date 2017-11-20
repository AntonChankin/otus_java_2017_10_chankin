package com.antonchankin.otus;

import com.antonchankin.otus.annotations.After;
import com.antonchankin.otus.annotations.Before;
import com.antonchankin.otus.annotations.Test;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Tester {

    public boolean runTests(String name) {
        boolean isSuccessful;
        boolean isClass = false;
        try {
            Class clazz = Class.forName(name);
            log.debug("The class is " + clazz);
            isClass = true;
        } catch (ClassNotFoundException e) {
            log.debug("The " + name + " is not a Class");
        }
        if (isClass) {
            isSuccessful = runTestsByClass(name);
        } else {
            isSuccessful = runTestsByPackage(name);
        }
        return isSuccessful;
    }

    public boolean runTestsByClass(String name) {
        boolean isSuccessful = true;
        log.info("Running tests for Class " + name);
        try {
            Class<?> clazz = Class.forName(name);
            Map<Class<? extends Annotation>,List<Method>> map = getMethodsMap(clazz);
            for (Method test : map.get(Test.class)) {
                isSuccessful = runTest(test, clazz, map) && isSuccessful;
            }
        } catch (ClassNotFoundException e) {
            log.error("No such class: " + name);
            isSuccessful = false;
        } catch (IllegalAccessException e) {
            log.error("Cannot access default constructor for class: " + name, e);
            isSuccessful = false;
        } catch (InstantiationException e) {
            log.error("Cannot instantiate class " + name + " with default constructor", e);
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private boolean runTest(Method test, Class<?> clazz, Map<Class<? extends Annotation>, List<Method>> map) throws IllegalAccessException, InstantiationException {
        boolean isSuccessful;
        log.info("Running test " + test.getName());
        try {
            Object instance = clazz.newInstance();
            runMethodWithAnnotation(map, instance, Before.class);
            log.info("Running test " + test.getName() + "...");
            Object result = test.invoke(instance);
            if (result instanceof Boolean){
                isSuccessful = (Boolean)result;
                log.info("Result of " + test.getName() + " is " + result);
            } else {
                isSuccessful = false;
                log.info("Test " + test.getName() + " failed");
            }
            runMethodWithAnnotation(map, instance, After.class);
        } catch (InvocationTargetException e) {
            log.error("Cannot invoke test method", e);
            isSuccessful = false;
        }
        return isSuccessful;
    }

    private void runMethodWithAnnotation(Map<Class<? extends Annotation>, List<Method>> map, Object instance, Class<? extends Annotation> clazz) throws IllegalAccessException {
        for (Method method : map.get(clazz)) {
            try {
                log.info("Invoking method " + method.getName());
                method.invoke(instance);
            } catch (InvocationTargetException e) {
                log.error("Cannot invoke method " + method.getName(), e);
            }
        }
    }

    private static Map<Class<? extends Annotation>,List<Method>> getMethodsMap(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        Map<Class<? extends Annotation>,List<Method>> result = new HashMap<>(3);
        result.put(Before.class, new ArrayList<>());
        result.put(After.class, new ArrayList<>());
        result.put(Test.class, new ArrayList<>());
        for (Method method : methods) {
            for (Map.Entry<Class<? extends Annotation>, List<Method>> entry : result.entrySet()) {
                if (method.isAnnotationPresent(entry.getKey())) {
                    entry.getValue().add(method);
                }
            }
        }
        return result;
    }

    public boolean runTestsByPackage(String name) {
        boolean isSuccessful;
        log.info("Running tests for package " + name);
        try {
            log.debug("Getting class loaders...");
            List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());
            log.debug("Initializing reflections...");
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(name))));
            Set<String> classes = reflections.getAllTypes();
            isSuccessful = true;
            for (String clazz : classes) {
                isSuccessful = runTestsByClass(clazz) && isSuccessful;
            }
        } catch (Exception e) {
            log.error("Failed to test package " + name, e);
            isSuccessful = false;
        }
        return isSuccessful;
    }
}
