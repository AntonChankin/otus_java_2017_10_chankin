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
        boolean isSuccessful = false;
        boolean isClass = false;
        try {
            Class clazz = Class.forName(name);
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
        boolean isSuccessful = false;
        boolean isFirst = true;
        log.info("Running tests for Class " + name);
        try {
            Class<?> clazz = Class.forName(name);
            Map<Class<? extends Annotation>,List<Method>> map = getMethodsMap(clazz);
            for (Method test : map.get(Test.class)) {
                log.info("Running test " + test.getName());
                try {
                    Object instance = clazz.newInstance();
                    for (Method before : map.get(Before.class)) {
                        try {
                            log.info("Invoking before method " + before.getName());
                            before.invoke(instance);
                        } catch (InvocationTargetException e) {
                            log.error("Cannot invoke before method " + before.getName(), e);
                        }
                    }
                    log.info("Running test " + test.getName() + "...");
                    Object result = test.invoke(instance);
                    if (result instanceof Boolean){
                        if (isFirst) {
                            isSuccessful = (Boolean)result;
                            isFirst = false;
                        } else {
                            isSuccessful = (Boolean)result && isSuccessful;
                        }
                        log.info("Result of " + test.getName() + " is " + result);
                    } else {
                        isSuccessful = false;
                        log.info("Test " + test.getName() + " failed");
                    }

                    for (Method after : map.get(After.class)) {
                        try {
                            log.info("Invoking after method " + after.getName());
                            after.invoke(instance);
                        } catch (InvocationTargetException e) {
                            log.error("Cannot invoke after method " + after.getName(), e);
                        }
                    }

                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("No such class: " + name);
        } catch (IllegalAccessException e) {
            log.error("Cannot access default constructor for class: " + name, e);
            isSuccessful = false;
        } catch (InstantiationException e) {
            log.error("Cannot instantiate class " + name + " with default constructor", e);
            isSuccessful = false;
        }
        return isSuccessful;
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
