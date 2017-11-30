package com.antonchankin.otus.hw06.api;

import com.antonchankin.otus.hw06.model.Transaction;

public interface WithdrawLogic {
    void setCashDispenser(CashDispenser dispenser);

    boolean dispenseLargeDenominations(Transaction transaction);

    boolean dispenseWithSpare(Transaction transaction);
}
