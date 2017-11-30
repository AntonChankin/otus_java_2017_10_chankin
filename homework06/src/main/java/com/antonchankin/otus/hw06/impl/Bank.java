package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.BankConnectionState;
import com.antonchankin.otus.hw06.api.BankConnector;

public class Bank implements BankConnector {

    @Override
    public BankConnectorContext connectToBank(String host, int port, String user, String password) {
        BankConnectorContext context = new BankConnectorContext();
        BankConnectionState state;
        if (connect(host, port, user, password)) {
            state = new MockConnectedState(host, port, user, password);
        } else {
            state = new FailedState();
        }
        state.connect(context);
        return context;
    }

    public boolean connect(String host, int port, String user, String password) {
        return true;
    }
}
