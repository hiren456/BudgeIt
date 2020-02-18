package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFiltererFactory;

public class EntryFetcherFactory {
    public static EntryFetcher createEntryFetcher(){
        EntryListFilterer filter = EntryListFiltererFactory.createEntryListFilterer();
        return new DefaultEntryFetcher(filter);
    }
}
