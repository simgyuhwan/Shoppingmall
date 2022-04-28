package com.growing.sgh.common.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
public class Money {

    private BigDecimal amount;

    public Money(BigDecimal amount){
        this.amount = amount;
    }

    public static Money of(Long amount){ return new Money(new BigDecimal(amount));}

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
