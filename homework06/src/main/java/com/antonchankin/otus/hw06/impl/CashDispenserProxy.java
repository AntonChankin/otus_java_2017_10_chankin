package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CartridgeChangeObserver;
import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.model.CashUnit;

import java.util.List;
import java.util.Map;

public class CashDispenserProxy implements CashDispenser, CartridgeChangeObserver {
    CashDispenser real;
    Map<Integer, String> denominationsNames;
    Map<Integer, Integer> denominations;
    Integer maxDenomination;
    Integer minDenomination;

    public CashDispenserProxy(CashDispenser real) {
        this.real = real;
        this.denominationsNames = real.getDenominationsNames();
        this.denominations = real.getDenominations();
        this.maxDenomination = real.getMaxDenomination();
        this.minDenomination = real.getMinDenomination();
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
        return denominationsNames;
    }

    @Override
    public List<CashUnit> getAvailable() {
        return real.getAvailable();
    }

    @Override
    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    @Override
    public int getMaxDenomination() {
        return maxDenomination;
    }

    @Override
    public int getMinDenomination() {
        return minDenomination;
    }

    @Override
    public void onCartridgeChanged() {
        this.denominationsNames = real.getDenominationsNames();
        this.denominations = real.getDenominations();
        this.maxDenomination = real.getMaxDenomination();
        this.minDenomination = real.getMinDenomination();
    }
}
