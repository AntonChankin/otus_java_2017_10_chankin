package com.antonchankin.otus.hw02;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Memory Measurement App
 *
 */
public class App 
{
    private static final int PRECISION = 10000;
    private static final int SIZE = 20_000_000;

    public static void main( String[] args )
    {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        System.out.println("Measuring empty String");
        System.out.println("Result: " + measure(new String(""), 0));
        System.out.println("Measuring Dummy");
        System.out.println("Result: " + measure(new Dummy(), 0));
        System.out.println("Measuring Object");
        System.out.println("Result: " + measure(new Object(), 0));
        for (int size = 0; size < 10; size++) {
            System.out.println("Measuring array of " + size + " elements");
            System.out.println("Result: " + measure(new Object[0], size));
        }
    }

    private static BigDecimal measure(Object object, int size) {
        Runtime.getRuntime().gc();
        BigDecimal answer = null;
        if (object != null) {
            BigDecimal total = null;
            MathContext context = null;
            Object[] array = new Object[SIZE];
            System.out.println("New array of size: " + array.length + " created");
            Runtime runtime = Runtime.getRuntime();
            long start = runtime.totalMemory() - runtime.freeMemory();
            if (object instanceof String) {
                array = fillWithEmptyStrings(array);
            } else if (object instanceof Dummy) {
                array = fillWithDummies(array);
            } else if (object.getClass().isArray()) {
                array = fillWithArrays(array, size);
            } else {
                array = fillWithObjects(array);
            }
            System.out.println("Created " + array.length + " objects.");
            long finish = runtime.totalMemory() - runtime.freeMemory();
            total = new BigDecimal(finish - start);
            context = new MathContext(PRECISION, RoundingMode.HALF_UP);
            answer = total.divide(BigDecimal.valueOf(SIZE), context);
        }
        Runtime.getRuntime().gc();
        return answer;
    }

    private static Object[] fillWithEmptyStrings(Object[] array){
        System.out.println("Starting Strings loop");
        for (int i = 0; i < SIZE; i++) {
            array[i] = new String(new char[0]); //without String pool
        }
        return array;
    }

    private static Object[] fillWithObjects(Object[] array){
        System.out.println("Starting Objects loop");
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Object();
        }
        return array;
    }

    private static Object[] fillWithDummies(Object[] array){
        System.out.println("Starting Dummies loop");
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Dummy();
        }
        return array;
    }

    private static Object[] fillWithArrays(Object[] array, int numberOfElements){
        System.out.println("Starting Arrays of size " + numberOfElements + " loop");
        for (int i = 0; i < SIZE; i++) {
            array[i] = new Object[numberOfElements];
        }
        return array;
    }
}
