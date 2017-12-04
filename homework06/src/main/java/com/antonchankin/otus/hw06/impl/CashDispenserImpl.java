package com.antonchankin.otus.hw06.impl;

import com.antonchankin.otus.hw06.api.CartredgeChangeSubject;
import com.antonchankin.otus.hw06.api.CartridgeChangeObserver;
import com.antonchankin.otus.hw06.api.CashDispenser;
import com.antonchankin.otus.hw06.model.Cartridge;
import com.antonchankin.otus.hw06.model.CashUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashDispenserImpl implements CashDispenser, CartredgeChangeSubject {
    private Logger log = LoggerFactory.getLogger(CashDispenserImpl.class);
    private List<Cartridge> cartridges;
    private CartridgeChangeObserver listener;

    public CashDispenserImpl() {
        this.cartridges = new ArrayList<>(0);
    }

    public CashDispenserImpl(List<Cartridge> cartridges) {
        this.cartridges = cartridges;
    }

    public void load(Cartridge cartridge){
        if (cartridge != null) {
            cartridges.add(cartridge);
            listener.onCartridgeChanged();
        }
    }

    public void replace(Cartridge empty, Cartridge full){
        if (cartridges.remove(empty)) {
            cartridges.add(full);
            listener.onCartridgeChanged();
        }
    }

    public void remove(Cartridge cartridge){
        if (cartridge != null) {
            cartridges.remove(cartridge);
            listener.onCartridgeChanged();
        }
    }

    @Override
    public boolean dispense(CashUnit unit) {
        Cartridge cartridge = null;
        boolean isDispensed = false;
        for (Cartridge element : cartridges) {
            if(element.getId() == unit.getDenominationId()){
                cartridge = element;
                break;
            }
        }
        if (cartridge != null && cartridge.getAmount() > unit.getAmount()) {
            cartridge.setAmount(cartridge.getAmount() - unit.getAmount());
            log.info("Dispensed " + unit.getAmount() + " of " + cartridge.getDenominationName());
            isDispensed = true;
        }
        return isDispensed;
    }

    @Override
    public boolean dispense(List<CashUnit> units) {
        boolean isDispensed = false;
        boolean isValid = true;
        try {
            for (CashUnit unit : units) {
                Cartridge cartridge = null;
                for (Cartridge element : cartridges) {
                    if(element.getId() == unit.getDenominationId()){
                        cartridge = element;
                        break;
                    }
                }
                if (cartridge != null && cartridge.getAmount() > unit.getAmount()) {
                    isValid = isValid && cartridge.getAmount() > unit.getAmount();
                }
            }
            for (CashUnit unit : units) {
                isDispensed = true;
                Cartridge cartridge = null;
                for (Cartridge element : cartridges) {
                    if(element.getId() == unit.getDenominationId()){
                        cartridge = element;
                        break;
                    }
                }
                log.info("Dispensed " + unit.getAmount() + " of " + cartridge.getDenominationName());
                cartridge.setAmount(cartridge.getAmount() - unit.getAmount());
            }
        } catch (Exception e) {
            log.error("Cannot dispense", e);
            isDispensed = false;
        }
        return isDispensed;
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
            denominations.put(cartridge.getDenomination(), cartridge.getId());
        }
        return denominations;
    }

    @Override
    public int getMaxDenomination() {
        int max = 0;
        for (Cartridge cartridge : cartridges) {
            max = cartridge.getDenomination() > max ? cartridge.getDenomination() : max;
        }
        return max;
    }

    @Override
    public int getMinDenomination() {
        int min = 0;
        for (Cartridge cartridge : cartridges) {
            min = cartridge.getDenomination() < min ? cartridge.getDenomination() : min;
        }
        return min;
    }

    @Override
    public void attach(CartridgeChangeObserver observer) {
        listener = observer;
    }
}
