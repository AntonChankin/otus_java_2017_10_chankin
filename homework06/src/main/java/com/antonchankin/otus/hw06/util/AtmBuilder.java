package com.antonchankin.otus.hw06.util;

import com.antonchankin.otus.hw06.ATM;
import com.antonchankin.otus.hw06.api.BankConnector;
import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.api.WithdrawLogic;
import com.antonchankin.otus.hw06.impl.MockBank;
import com.antonchankin.otus.hw06.impl.BankConnectorContext;
import com.antonchankin.otus.hw06.impl.CashDispenserImpl;
import com.antonchankin.otus.hw06.impl.WithdrawSingleton;
import com.antonchankin.otus.hw06.model.Cartridge;

import java.util.List;

public class AtmBuilder {
    private String host;
    private Integer port;
    private String user;
    private String password;
    private List<Cartridge> cartridges;

    public AtmBuilder() {

    }

    public AtmBuilder host(String host) {
        this.host = host;
        return this;
    }

    public AtmBuilder user(String user) {
        this.user = user;
        return this;
    }

    public AtmBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AtmBuilder port(int port) {
        this.port = port;
        return this;
    }

    public AtmBuilder cartriges(List<Cartridge> cartridges) {
        this.cartridges = cartridges;
        return this;
    }

    public ATM build(){
        ATM atm = null;
        if (host != null && port != null && user != null && password != null && cartridges != null) {
            CashDispenser dispenser = new CashDispenserImpl(cartridges);
            WithdrawLogic logic = WithdrawSingleton.getInstance();
            logic.setCashDispenser(dispenser);
            BankConnector connector = new MockBank();
            BankConnectorContext context = connector.connectToBank(host, port, user, password);
            atm = new ATM(context, logic);
        } else {
            throw new IllegalStateException("Not initialized");
        }
        return atm;
    }
}
