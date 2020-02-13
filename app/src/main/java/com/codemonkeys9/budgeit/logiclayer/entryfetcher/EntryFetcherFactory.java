package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.database.Database;

public class EntryFetcherFactory {
    public static EntryFetcher createEntryFetcher(Database database, EntryListFilterer filter){
        return new DefaultEntryFetcher(database,filter);
    }
}
