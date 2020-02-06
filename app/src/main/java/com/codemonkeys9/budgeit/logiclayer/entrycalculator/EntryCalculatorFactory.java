package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

public class EntryCalculatorFactory {

    public EntryCalculator createEntryCalculator(){
        return new DefaultEntryCalculator();
    }
}
