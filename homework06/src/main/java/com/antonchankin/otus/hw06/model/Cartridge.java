package com.antonchankin.otus.hw06.model;

import java.util.Objects;

public final class Cartridge {
    private final int id;
    private final String denominationName;
    private final int amount;
    private final int denomination;


    public Cartridge(int id, String denominationName, int amount, int denomination) {
        this.id = id;
        this.denominationName = denominationName;
        this.amount = amount;
        this.denomination = denomination;
    }

    public int getId() {
        return id;
    }

    public String getDenominationName() {
        return denominationName;
    }

    public int getAmount() {
        return amount;
    }

    public int getDenomination() {
        return denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cartridge)) return false;
        Cartridge cartridge = (Cartridge) o;
        return id == cartridge.id &&
                denomination == cartridge.denomination &&
                Objects.equals(denominationName, cartridge.denominationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denominationName, denomination);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cartridge{");
        sb.append("id=").append(id);
        sb.append(", denominationName='").append(denominationName).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", denomination=").append(denomination);
        sb.append('}');
        return sb.toString();
    }
}
