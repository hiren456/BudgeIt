package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

public class UIEntryFetcherFactory {
    public static UIEntryFetcher createUIEntryFetcher(){
        EntryFetcher fetcher = EntryFetcherFactory.createEntryFetcher();
        return new DefaultUIEntryFetcher(fetcher);
    }
}
