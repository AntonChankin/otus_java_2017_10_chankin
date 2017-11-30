package com.antonchankin.otus.hw06.api;

public interface BankConnectionState extends Validator, CashWithdrawObserver {
    public void connect(ConnectionContext context);
}
