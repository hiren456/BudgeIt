package com.codemonkeys9.budgeit.dso.amount;

public class AmountFactory {

    /*
    Creates an amount from a string such as "99.99"
     */
    public static Amount fromString(String amount){

        return new DefaultAmount(amount);
    }

    /*
    Creates an amount from an int such as 9999
    this would represent the amount 99.99
     */
    public static Amount fromInt(int amount){

        return new DefaultAmount(amount);
    }
}
