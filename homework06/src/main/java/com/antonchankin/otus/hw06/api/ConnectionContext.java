package com.antonchankin.otus.hw06.api;

public interface ConnectionContext {
    void setState(BankConnectionState state);

    BankConnectionState getState();
}
