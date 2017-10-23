package com.antonchankin.otus.hw02;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.MathContext;

public class MemoryMeasurers implements Measurers {
    public BigDecimal measure(Object object) {
        BigDecimal answer = null;
        Runtime runtime = Runtime.getRuntime();

        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 20_000_000;

        System.out.println("Starting the loop");
        Object[] array = new Object[size];
        long start = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < size; i++) {
            array[i] = new Object();
            //array[i] = new String(""); //String pool
            //array[i] = new String(new char[0]); //without String pool
            //array[i] = new MyClass();
        }
        System.out.println("Created " + size + " objects.");
        long finish = runtime.totalMemory() - runtime.freeMemory();
        BigDecimal total = new BigDecimal(finish - start);
        MathContext context = new MathContext()
        answer = total.divide(BigDecimal.valueOf(size));

        return answer;
    }
}
