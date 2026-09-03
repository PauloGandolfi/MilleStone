package io.github.paulogandolfi.milestone.common.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import io.github.paulogandolfi.milestone.common.error.DomainException;
import io.github.paulogandolfi.milestone.common.error.ErrorCodes;

public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("BRL");

    public Money(BigDecimal amount) {
        this(amount, DEFAULT_CURRENCY);
    }

    public Money {
        if (amount == null) {
            throw invalid("Money amount must not be null");
        }
        if (currency == null) {
            throw invalid("Money currency must not be null");
        }

        amount = amount.setScale(SCALE, ROUNDING_MODE);

        if (amount.signum() < 0) {
            throw invalid("Money amount must not be negative");
        }
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public boolean isPositive() {
        return amount.signum() > 0;
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    private void requireSameCurrency(Money other) {
        if (other == null) {
            throw invalid("Money operand must not be null");
        }
        if (!currency.equals(other.currency)) {
            throw invalid("Money operations require matching currencies");
        }
    }

    private static DomainException invalid(String message) {
        return new DomainException(ErrorCodes.INVALID_MONEY, message);
    }
}
