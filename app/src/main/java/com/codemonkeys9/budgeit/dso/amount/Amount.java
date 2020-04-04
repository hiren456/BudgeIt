package com.codemonkeys9.budgeit.dso.amount;

/*
This class represents a monetary amount
all amounts are positive numbers
 */
public interface Amount {

    /*
    Returns the amount as a string in
    the form of "ddd.cc"
     */
    String getDisplay();

    String getAbsoluteValueDisplay();

    /*
    Returns the amount as a fixed point integer
    ie. 99.99 gets returned as 9999
     */
    int getValue();

    boolean equals(Amount other);
    Amount clone();
}
