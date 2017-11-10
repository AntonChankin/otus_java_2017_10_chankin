package com.antonchankin.otus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Crasher {
    public static void main(String[] args) throws Exception {
        Crasher memoryTest = new Crasher();
        memoryTest.generateOOM();
    }

    public void generateOOM() throws Exception {
        int iteratorValue = 20;
        log.info("\n=================> OOM test started..\n");
        for (int outerIterator = 1; outerIterator < 20; outerIterator++) {
            log.info("Iteration " + outerIterator + " Free Mem: " + Runtime.getRuntime().freeMemory());
            int loop1 = 2;
            int[] memoryFillIntVar = new int[iteratorValue];
            // feel memoryFillIntVar array in loop..
            do {
                memoryFillIntVar[loop1] = 0;
                loop1--;
            } while (loop1 > 0);
            iteratorValue = iteratorValue * 5;
            log.info("\nRequired Memory for next loop: " + iteratorValue);
            Thread.sleep(1000);
        }
    }

}
