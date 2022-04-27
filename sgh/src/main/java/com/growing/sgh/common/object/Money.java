package com.growing.sgh.common.object;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
public class Money {
    private BigDecimal amount;

    public Money(BigDecimal amount){
        this.amount = amount;
    }

    public Money(long amount){
        this(new BigDecimal(amount));
    }

    public Money add(Money money){
        return new Money(this.amount.add(money.amount));
    }

    public Money multiply(Money money){
        return new Money(this.amount.multiply(money.amount));
    }


}
