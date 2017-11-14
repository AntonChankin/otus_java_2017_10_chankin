package com.antonchankin.otus;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Tester {

    public boolean runTests(String name) {
        boolean isSuccessful = false;
        //TODO: Method detecting is name is class or package
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
        log.info("Running tests for Class " + name);
        
        return isSuccessful;
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
