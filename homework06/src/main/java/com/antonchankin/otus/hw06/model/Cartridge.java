package com.antonchankin.otus.hw06.model;

import java.util.Objects;

public final class Cartridge {
    private final int id;
    private final String denomination;
    private final int amount;

    public Cartridge(int id, String denomination, int amount) {
        this.id = id;
        this.denomination = denomination;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cartridge)) return false;
        Cartridge cartridge = (Cartridge) o;
        return id == cartridge.id &&
                amount == cartridge.amount &&
                Objects.equals(denomination, cartridge.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denomination, amount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cartridge{");
        sb.append("id=").append(id);
        sb.append(", denomination='").append(denomination).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
