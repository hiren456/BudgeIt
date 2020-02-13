package com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler;

import com.codemonkeys9.budgeit.logiclayer.ParameterConverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

public class UICalculaterRequestHandlerFactory {
    public static UICalculateRequestHandler createUICalculateRequestHandler(ParameterConverter converter, EntryFetcher fetcher, EntryCalculator calculator){
        return new DefaultUICalculateRequestHandler(converter ,fetcher, calculator);
    }
}
