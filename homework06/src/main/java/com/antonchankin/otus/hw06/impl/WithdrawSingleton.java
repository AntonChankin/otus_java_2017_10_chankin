package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.api.WithdrawLogic;
import com.antonchankin.otus.hw06.model.Transaction;

public class WithdrawSingleton implements WithdrawLogic {
    private static volatile WithdrawSingleton instance;
    private CashDispenser dispenser;

    public static WithdrawSingleton getInstance() {
        WithdrawSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (WithdrawSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WithdrawSingleton();
                }
            }
        }
        return localInstance;
    }

    private WithdrawSingleton(){

    }

    @Override
    public void setCashDispenser(CashDispenser dispenser) {
        this.dispenser = dispenser;
    }

    @Override
    public boolean dispenseLargeDenominations(Transaction transaction) {
        return false;
    }

    @Override
    public boolean dispenseWithSpare(Transaction transaction) {
        return false;
    }
}
