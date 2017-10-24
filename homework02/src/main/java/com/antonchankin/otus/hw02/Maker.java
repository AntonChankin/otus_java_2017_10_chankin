package com.antonchankin.otus.hw02;


public class Maker {
    private static final int SIZE = 20_000_000;

    static Object[] fill(Object[] array, Object object, int numberOfElements){
        if (object instanceof String) {
            array = fillWithEmptyStrings(array);
        } else if (object instanceof Dummy) {
            array = fillWithDummies(array);
        } else if (object.getClass().isArray()) {
            array = fillWithArrays(array, numberOfElements);
        } else {
            array = fillWithObjects(array);
        }
        return array;
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

    public static int getSize() {
        return SIZE;
    }
}
