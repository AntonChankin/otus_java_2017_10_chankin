package com.antonchankin.otus;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        GCListener.installGCMonitoring();
        Crasher memoryTest = new Crasher();
        memoryTest.generateOOM();
    }
}
