package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import com.codemonkeys9.budgeit.LogicLayer.Database.Database;

public class EntryFetcherFactory {
    public EntryFetcher createEntryFetcher(Database database){
        return new DefaultEntryFetcher(database);
    }
}
