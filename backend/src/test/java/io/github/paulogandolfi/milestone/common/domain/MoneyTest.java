package io.github.paulogandolfi.milestone.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;

import io.github.paulogandolfi.milestone.common.error.DomainException;
import io.github.paulogandolfi.milestone.common.error.ErrorCodes;

class MoneyTest {

    @Test
    void defaultsToBrlAndNormalizesScaleUsingHalfEven() {
        Money money = new Money(new BigDecimal("10.125"));

        assertThat(money.amount()).isEqualByComparingTo("10.12");
        assertThat(money.amount().scale()).isEqualTo(2);
        assertThat(money.currency()).isEqualTo(Currency.getInstance("BRL"));
    }

    @Test
    void addsMoneyWithTheSameCurrency() {
        Money result = new Money(new BigDecimal("10.10"))
                .plus(new Money(new BigDecimal("2.35")));

        assertThat(result.amount()).isEqualByComparingTo("12.45");
    }

    @Test
    void subtractsMoneyWithoutAllowingNegativeResults() {
        Money result = new Money(new BigDecimal("10.00"))
                .minus(new Money(new BigDecimal("3.25")));

        assertThat(result.amount()).isEqualByComparingTo("6.75");

        assertThatThrownBy(() -> new Money(new BigDecimal("3.00"))
                .minus(new Money(new BigDecimal("4.00"))))
                .isInstanceOf(DomainException.class)
                .hasMessage("Money amount must not be negative");
    }

    @Test
    void exposesPositiveZeroAndComparisonOperations() {
        Money zero = Money.zero();
        Money ten = new Money(new BigDecimal("10.00"));

        assertThat(zero.isZero()).isTrue();
        assertThat(zero.isPositive()).isFalse();
        assertThat(ten.isPositive()).isTrue();
        assertThat(ten.compareTo(zero)).isPositive();
    }

    @Test
    void rejectsNullNegativeAndDifferentCurrencyOperations() {
        assertThatThrownBy(() -> new Money(null))
                .isInstanceOf(DomainException.class)
                .extracting("code")
                .isEqualTo(ErrorCodes.INVALID_MONEY);

        assertThatThrownBy(() -> new Money(new BigDecimal("-0.01")))
                .isInstanceOf(DomainException.class)
                .hasMessage("Money amount must not be negative");

        Money brl = new Money(new BigDecimal("1.00"));
        Money usd = new Money(new BigDecimal("1.00"), Currency.getInstance("USD"));

        assertThatThrownBy(() -> brl.plus(usd))
                .isInstanceOf(DomainException.class)
                .hasMessage("Money operations require matching currencies");
    }
}
