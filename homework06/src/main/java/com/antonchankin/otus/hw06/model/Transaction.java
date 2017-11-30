package com.antonchankin.otus.hw06.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public final class Transaction {
    private final BigInteger account;
    private final Integer amount;
    private final Date timestamp;

    public Transaction(BigInteger account, Integer amount, Date timestamp) {
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigInteger getAccount() {
        return account;
    }

    public Integer getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, amount, timestamp);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Transaction{");
        sb.append("account=").append(account);
        sb.append(", amount=").append(amount);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
