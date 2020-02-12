package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParser;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrderer;

public class UIFetchRequestHandlerFactory {
    public static UIFetchRequestHandler createUIFetchRequestHandler(DateParser dateParser, EntryFetcher fetcher, EntryListOrderer orderer){
        return new DefaultUIFetchRequestHandler(dateParser,fetcher,orderer);
    }
}
