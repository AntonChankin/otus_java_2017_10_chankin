package com.antonchankin.otus;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        int size = 50 * 1000 * 100;
        GCListener.installGCMonitoring();
        Crasher memoryTest = new Crasher();
        memoryTest.generateOOM();
    }
}
