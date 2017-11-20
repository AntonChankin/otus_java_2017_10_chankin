package com.antonchankin.otus;

/**
 * Let's test the Testing Framework
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        Tester tester = new Tester();
        String name = "com.antonchankin.otus.dummies";
        boolean testResult = tester.runTests(name);
        System.out.println("Test result for " + name + " is " + testResult);
    }
}
