package com.antonchankin.otus.hw06.util;

import com.antonchankin.otus.hw06.ATM;
import com.antonchankin.otus.hw06.model.Cartridge;

import java.util.List;

public class AtmBuilder {
    private ATM atm = new ATM();

    public AtmBuilder() {

    }

    public AtmBuilder host(String host) {

        return this;
    }

    public AtmBuilder user(String user) {

        return this;
    }

    public AtmBuilder password(String password) {

        return this;
    }

    public AtmBuilder port(int port) {

        return this;
    }

    public AtmBuilder cartriges(List<Cartridge> cartridges) {

        return this;
    }

    public ATM build(){
        return atm;
    }
}
