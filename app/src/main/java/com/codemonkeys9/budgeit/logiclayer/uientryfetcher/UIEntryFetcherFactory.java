package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverterFactory;

public class UIEntryFetcherFactory {
    public static UIEntryFetcher createUIEntryFetcher(){
        ParameterConverter converter = ParameterConverterFactory.createParameterConverter();
        EntryFetcher fetcher = EntryFetcherFactory.createEntryFetcher();
        return new DefaultUIEntryFetcher(converter,fetcher);
    }
}
