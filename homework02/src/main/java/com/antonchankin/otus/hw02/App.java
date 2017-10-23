package com.antonchankin.otus.hw02;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Measurers measurer = new MemoryMeasurers();
        Object object = new Object();
        System.out.println( "Measuring Object");
        System.out.println("Result is " + measurer.measure(object) + " bytes");
        object = new String("");
        System.out.println( "Measuring String");
        System.out.println("Result is " + measurer.measure(object) + " bytes");
        object = new Dummy();
        System.out.println( "Measuring Dummy");
        System.out.println("Result is " + measurer.measure(object) + " bytes");
    }
}
