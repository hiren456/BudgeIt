package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

public class UICalculatorFactory {
    public static UICalculator createUICalculator(){
        EntryCalculator calculator = EntryCalculatorFactory.createEntryCalculator();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        return new DefaultUICalculator(fetcher, calculator);
    }
}
