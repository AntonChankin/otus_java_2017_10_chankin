package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.model.Cartridge;
import com.antonchankin.otus.hw06.model.CashUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashDispenserImpl implements CashDispenser {
    private List<Cartridge> cartridges;

    public CashDispenserImpl() {
        this.cartridges = new ArrayList<>(0);
    }

    public CashDispenserImpl(List<Cartridge> cartridges) {
        this.cartridges = cartridges;
    }

    public void load(Cartridge cartridge){
        cartridges.add(cartridge);
    }

    public void replace(Cartridge empty, Cartridge full){
        if (cartridges.remove(empty)) {
            cartridges.add(full);
        }
    }

    public void remove(Cartridge cartridge){
        cartridges.remove(cartridge);
    }

    @Override
    public boolean dispense(CashUnit unit) {
        return false;
    }

    @Override
    public boolean dispense(List<CashUnit> units) {
        return false;
    }

    @Override
    public Map<Integer, String> getDenominationsNames() {
        Map<Integer, String> denominations = new HashMap<>(cartridges.size());
        for (Cartridge cartridge : cartridges) {
            denominations.put(cartridge.getId(), cartridge.getDenominationName());
        }
        return denominations;
    }

    @Override
    public List<CashUnit> getAvailable() {
        List<CashUnit> units = new ArrayList<>(cartridges.size());
        for (Cartridge cartridge : cartridges) {
            units.add(new CashUnit(cartridge.getId(), cartridge.getAmount()));
        }
        return units;
    }

    @Override
    public Map<Integer, Integer> getDenominations() {
        Map<Integer, Integer> denominations = new HashMap<>(cartridges.size());
        for (Cartridge cartridge : cartridges) {
            denominations.put(cartridge.getId(), cartridge.getDenomination());
        }
        return denominations;
    }
}
