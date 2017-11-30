package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.BankConnectionState;
import com.antonchankin.otus.hw06.api.ConnectionContext;
import com.antonchankin.otus.hw06.model.Transaction;

public class FailedState implements BankConnectionState {

    @Override
    public void connect(ConnectionContext context) {
        System.out.println("Connection to Bank failed");
        context.setState(this);
    }

    @Override
    public boolean validate(Transaction transaction) {
        return false;
    }

    @Override
    public void notify(Transaction transaction) {
        throw new IllegalStateException("ATM is not connection");
    }
}
