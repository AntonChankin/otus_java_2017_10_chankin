package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CartridgeChangeObserver;
import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.model.CashUnit;

import java.util.List;
import java.util.Map;

public class CashDispenserProxy implements CashDispenser, CartridgeChangeObserver {
    CashDispenser real;

    public CashDispenserProxy(CashDispenser real) {
        this.real = real;
    }

    @Override
    public boolean dispense(CashUnit unit) {
        return real.dispense(unit);
    }

    @Override
    public boolean dispense(List<CashUnit> units) {
        return real.dispense(units);
    }

    @Override
    public Map<Integer, String> getDenominationsNames() {
        return real.getDenominationsNames();   //TODO: #10
    }

    @Override
    public List<CashUnit> getAvailable() {
        return real.getAvailable();
    }

    @Override
    public Map<Integer, Integer> getDenominations() {
        return null;                    //TODO: #10
    }

    @Override
    public int getMaxDenomination() {
        return 0;                //TODO: #10
    }

    @Override
    public int getMinDenomination() {
        return 0;                 //TODO: #10
    }

    @Override
    public void onCartridgeChanged() {
        //TODO: #10 Reset
    }
}
