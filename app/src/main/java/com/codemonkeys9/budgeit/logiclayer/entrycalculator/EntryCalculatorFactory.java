package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

public class EntryCalculatorFactory {

    public static EntryCalculator createEntryCalculator(){
        return new DefaultEntryCalculator();
    }
}
