package com.antonchankin.otus.hw06;

import com.antonchankin.otus.hw06.api.ConnectionContext;
import com.antonchankin.otus.hw06.api.WithdrawLogic;

public class ATM {
    private ConnectionContext context;
    private WithdrawLogic logic;

    public ATM(ConnectionContext context, WithdrawLogic withdrawLogic) {
        this.context = context;
        this.logic = withdrawLogic;
    }


}
