package com.antonchankin.otus.hw06.api;

import com.antonchankin.otus.hw06.model.Transaction;

public interface CashWithdrawObserver {
    void notify(Transaction transaction);
}
