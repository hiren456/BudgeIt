package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

public class UIFetchRequestHandlerFactory {
    public static UIFetchRequestHandler createUIFetchRequestHandler(ParameterConverter converter, EntryFetcher fetcher){
        return new DefaultUIFetchRequestHandler(converter,fetcher);
    }
}
