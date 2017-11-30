package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.BankConnectionState;
import com.antonchankin.otus.hw06.api.ConnectionContext;

public class BankConnectorContext implements ConnectionContext {
    private BankConnectionState state;

    public BankConnectorContext() {
        this.state = null;
    }

    @Override
    public void setState(BankConnectionState state) {
        this.state = state;
    }

    @Override
    public BankConnectionState getState() {
        return state;
    }
}
