package com.antonchankin.otus.hw03;

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
        OtusArrayList<Integer> list = new OtusArrayList<>(5);
        
    }
}
