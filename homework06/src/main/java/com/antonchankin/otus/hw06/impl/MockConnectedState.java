package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.BankConnectionState;
import com.antonchankin.otus.hw06.api.ConnectionContext;
import com.antonchankin.otus.hw06.model.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MockConnectedState implements BankConnectionState {
    private String host;
    private int port;
    private String user;
    private String password;
    private Map<BigInteger,BigDecimal> accounts = new HashMap<>();

    public MockConnectedState(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    @Override
    public void connect(ConnectionContext context) {
        System.out.println("Connection to Bank established");
        context.setState(this);
    }

    @Override
    public boolean validate(Transaction transaction) {
        System.out.println("Connecting to " + host + ':' + port + " as " + user + "...");
        boolean isValid;
        BigDecimal amount = accounts.get(transaction.getAccount());
        isValid = amount != null && amount.intValue() >= transaction.getAmount();
        return isValid;
    }

    @Override
    public void notify(Transaction transaction) {
        boolean isValid;
        BigDecimal amount = accounts.get(transaction.getAccount());
        isValid = amount != null && amount.intValue() >= transaction.getAmount();
        if (isValid) {
            amount = amount.subtract(BigDecimal.valueOf(transaction.getAmount()));
            accounts.put(transaction.getAccount(), amount);
        }
    }
}
