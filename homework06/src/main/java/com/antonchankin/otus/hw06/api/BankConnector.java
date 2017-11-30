package com.antonchankin.otus.hw06.api;

import com.antonchankin.otus.hw06.impl.BankConnectorContext;

public interface BankConnector {
    BankConnectorContext connectToBank(String host, int port, String user, String password);
}
