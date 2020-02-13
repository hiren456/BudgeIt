package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.logiclayer.ParameterConverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrderer;

public class UIFetchRequestHandlerFactory {
    public static UIFetchRequestHandler createUIFetchRequestHandler(ParameterConverter converter, EntryFetcher fetcher, EntryListOrderer orderer){
        return new DefaultUIFetchRequestHandler(converter,fetcher,orderer);
    }
}
