package com.antonchankin.otus.hw06.api;

import com.antonchankin.otus.hw06.model.CashUnit;

import java.util.List;
import java.util.Map;

public interface CashDispenser {
    boolean dispense(CashUnit unit);

    boolean dispense(List<CashUnit> units);

    Map<Integer,String> getDenominationsNames();

    List<CashUnit> getAvailable();
}
