package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

public class UICalculatorFactory {
    public static UICalculator createUICalculator(EntryFetcher fetcher, EntryCalculator calculator){
        return new DefaultUICalculator(fetcher, calculator);
    }
}
