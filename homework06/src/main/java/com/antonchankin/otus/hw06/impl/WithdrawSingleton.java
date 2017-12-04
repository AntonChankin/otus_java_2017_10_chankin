package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.api.WithdrawLogic;
import com.antonchankin.otus.hw06.model.CashUnit;
import com.antonchankin.otus.hw06.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
        return dispense(transaction, false);
    }

    @Override
    public boolean dispenseWithSpare(Transaction transaction) {
        return dispense(transaction, true);
    }

    private boolean dispense(Transaction transaction, boolean withChange) {
        Integer amount = transaction.getAmount();
        List<CashUnit> amounts = new ArrayList<>();
        boolean isDispensed;
        if (amount > 0) {
            Map<Integer,Integer> denominations = dispenser.getDenominations();
            SortedSet<Integer> keys = new TreeSet<>(denominations.keySet());
            List<CashUnit> cashUnits = dispenser.getAvailable();
            boolean isMax = true;
            for (Integer key : keys) {
                Integer multilayer = amount / key;
                if(withChange) {
                    if(isMax) {
                        multilayer--;
                        isMax = false;
                    }
                }
                CashUnit found = null;
                for (CashUnit unit : cashUnits) {
                    if (unit.getDenominationId() == denominations.get(key)) {
                        found = unit;
                        break;
                    }
                }
                if (found != null) {
                    found = found.getAmount() > multilayer ? found : null;
                }
                if (found != null) {
                    amount -= multilayer * key;
                    amounts.add(new CashUnit(denominations.get(key), multilayer));
                }
            }
            isDispensed = dispenser.dispense(amounts);
        } else {
            isDispensed = false;
        }
        return isDispensed;
    }
}
