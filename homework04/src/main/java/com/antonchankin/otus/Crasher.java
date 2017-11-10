package com.antonchankin.otus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Crasher {
    private volatile int size = 0;

    public Crasher(int startSize) {
        this.size = startSize;
    }

    public static void main(String[] args) throws Exception {
        int size = 5 * 1000 * 1000;
        Crasher memoryTest = new Crasher(size);
        memoryTest.generateOOM();
    }

    public void generateOOM() throws Exception {
        log.info("Starting the loop");
        int iterationSize = size;
        while (true) {
            int local = iterationSize;
            Object[] array = new Object[local];
            log.info("Array of size: " + array.length + " created");

            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            log.info("Created " + local + " objects.");
            iterationSize = iterationSize * 10;
            log.info("Required Memory for next loop: " + iterationSize);
            Thread.sleep(1000);
        }
    }

}
