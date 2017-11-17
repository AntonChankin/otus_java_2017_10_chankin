package com.antonchankin.otus.dummies;

import com.antonchankin.otus.annotations.After;
import com.antonchankin.otus.annotations.Before;
import com.antonchankin.otus.annotations.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnotherTestDummy {

    @Before
    public void beforeMethodAlpha(){
        log.info("Before Method Alpha have been called");
    }

    @Before
    public void beforeMethodBeta(){
        log.info("Before Method Beta have been called");
    }
    @Test
    public boolean testMethodAlpha(){
        log.info("Test Method Alpha have been called");
        boolean isSuccessful = true;
        return isSuccessful;
    }
    @Test
    public boolean testMethodBeta(){
        log.info("Test Method Beta have been called");
        boolean isSuccessful = true;
        return isSuccessful;
    }
    @After
    public void afterMethodAlpha(){
        log.info("After Method Alpha have been called");
    }
    @After
    public void afterMethodBeta(){
        log.info("After Method Beta have been called");
    }
}
