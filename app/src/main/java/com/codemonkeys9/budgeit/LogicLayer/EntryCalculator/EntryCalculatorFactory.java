package com.codemonkeys9.budgeit.LogicLayer.EntryCalculator;

public class EntryCalculatorFactory {

    public EntryCalculator createEntryCalculator(){
        return new DefaultEntryCalculator();
    }
}
