package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;

public class UICalculatorFactory {
    public static UICalculator createUICalculator(){
        EntryCalculator calculator = EntryCalculatorFactory.createEntryCalculator();
        EntryFetcher fetcher = EntryFetcherFactory.createEntryFetcher();
        return new DefaultUICalculator(fetcher, calculator);
    }
}
