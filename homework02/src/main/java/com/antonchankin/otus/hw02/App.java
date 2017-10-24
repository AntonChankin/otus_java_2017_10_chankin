package com.antonchankin.otus.hw02;

import java.lang.management.ManagementFactory;

/**
 * Memory Measurement App
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("Measuring empty String");
        System.out.println("Result: " + Measurer.measure(new String("")));
        System.out.println("Measuring Dummy");
        System.out.println("Result: " + Measurer.measure(new Dummy()));
        System.out.println("Measuring Object");
        System.out.println("Result: " + Measurer.measure(new Object()));
        for (int size = 0; size < 4; size++) {
            System.out.println("Measuring array of " + size + " elements");
            System.out.println("Result: " + Measurer.measure(new Object[0], size));
        }
    }




}
