package com.codemonkeys9.budgeit.dso.amount;

import org.junit.Test;

import static org.junit.Assert.*;

public class AmountTest {
    @Test
    public void validAmountFromIntValueTest() {

        //Create valid amount
        int intAmount = 999;
        Amount amount = AmountFactory.fromInt(intAmount);

        assertEquals(intAmount,amount.getValue());
    }

    @Test
    public void validAmountFromIntDisplayTest() {

        //Create valid amount
        int intAmount = 999;
        Amount amount = AmountFactory.fromInt(intAmount);

        assertTrue(amount.getDisplay().equals("9.99"));
    }

    @Test
    public void validAmountFromStringValueTest() {

        //Create valid amount
        String stringAmount = "9.99";
        Amount amount = AmountFactory.fromString(stringAmount);

        assertEquals(999,amount.getValue());
    }

    @Test
    public void validAmountFromStringDisplayTest() {

        //Create valid amount
        String stringAmount = "9.99";
        Amount amount = AmountFactory.fromString(stringAmount);

        assertTrue(amount.getDisplay().equals("9.99"));
    }

    @Test
    public void equalsWithNo10sPlaceest() {

        //Create valid amount
        String stringAmount = ".99";
        Amount amount = AmountFactory.fromString(stringAmount);

        assertTrue(amount.getDisplay().equals(".99"));
    }

}
