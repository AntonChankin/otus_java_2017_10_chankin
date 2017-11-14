package com.antonchankin.otus.dummies;

import com.antonchankin.otus.annotations.After;
import com.antonchankin.otus.annotations.Before;
import com.antonchankin.otus.annotations.Test;

public class TestDummy {

    @Before
    public boolean beforeMethodAlpha(){
        boolean isSuccessful = false;
        return isSuccessful;
    }

    @Before
    public boolean beforeMethodBeta(){
        boolean isSuccessful = false;
        return isSuccessful;
    }
    @Test
    public boolean testMethodAlpha(){
        boolean isSuccessful = false;
        return isSuccessful;
    }
    @Test
    public boolean testMethodBeta(){
        boolean isSuccessful = false;
        return isSuccessful;
    }
    @After
    public boolean afterMethodAlpha(){
        boolean isSuccessful = false;
        return isSuccessful;
    }
    @After
    public boolean afterMethodBeta(){
        boolean isSuccessful = false;
        return isSuccessful;
    }
}
