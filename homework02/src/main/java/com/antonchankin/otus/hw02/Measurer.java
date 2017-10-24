package com.antonchankin.otus.hw02;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Measurer {
    private static final int PRECISION = 10000;

    static BigDecimal measure(Object object, int size) {
        Runtime.getRuntime().gc();
        BigDecimal answer = null;
        if (object != null) {
            BigDecimal total = null;
            MathContext context = null;
            Object[] array = new Object[Maker.getSize()];
            System.out.println("New array of size: " + array.length + " created");
            Runtime runtime = Runtime.getRuntime();
            long start = runtime.totalMemory() - runtime.freeMemory();
            Maker.fill(array, object, size);
            System.out.println("Created " + array.length + " objects.");
            long finish = runtime.totalMemory() - runtime.freeMemory();
            total = new BigDecimal(finish - start);
            context = new MathContext(PRECISION, RoundingMode.HALF_UP);
            answer = total.divide(BigDecimal.valueOf(Maker.getSize()), context);
        }
        Runtime.getRuntime().gc();
        return answer;
    }

    static BigDecimal measure(Object object){
        return measure(object, 0);
    }
}
