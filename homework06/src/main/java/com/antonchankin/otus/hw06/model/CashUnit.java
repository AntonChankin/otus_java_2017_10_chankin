package com.antonchankin.otus.hw06.model;

import java.util.Objects;

public final class CashUnit {
    private final int denominationId;
    private final int amount;

    public CashUnit(int denominationId, int amount) {
        this.denominationId = denominationId;
        this.amount = amount;
    }

    public int getDenominationId() {
        return denominationId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashUnit)) return false;
        CashUnit cashUnit = (CashUnit) o;
        return denominationId == cashUnit.denominationId &&
                amount == cashUnit.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominationId, amount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CashUnit{");
        sb.append("denominationId=").append(denominationId);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
