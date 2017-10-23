package com.antonchankin.otus.hw02;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MemoryMeasurers implements Measurers {

    private static final int PRECISION = 10000;
    private static final int SIZE = 20_000_000;

    public MemoryMeasurers() {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
    }

    public BigDecimal measure(Object object) {
        BigDecimal answer = null;
        Runtime runtime = Runtime.getRuntime();

        if (object != null) {
            System.out.println("Starting the loop for class " + object.getClass());
            Object[] array = new Object[SIZE];
            long start = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("New array of size: " + array.length + " created");
            try {
                for (int i = 0; i < SIZE; i++) {
                    array[i] = object.getClass().newInstance();
                }
                System.out.println("Created " + SIZE + " objects.");
                long finish = runtime.totalMemory() - runtime.freeMemory();
                BigDecimal total = new BigDecimal(finish - start);
                MathContext context = new MathContext(PRECISION, RoundingMode.HALF_EVEN);
                answer = total.divide(BigDecimal.valueOf(SIZE), context);
            } catch (InstantiationException e) {
                System.out.println("Cannot instantiate an object of class " + object.getClass());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("Default constructor is not public in class " + object.getClass());
                e.printStackTrace();
            }
        }
        return answer;
    }
}
