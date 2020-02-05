package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import com.codemonkeys9.budgeit.LogicLayer.EntryListFilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.LogicLayer.Database.Database;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParser;

public class EntryFetcherFactory {
    public EntryFetcher createEntryFetcher(Database database, DateParser dateParser, EntryListFilterer filter){
        return new DefaultEntryFetcher(database,dateParser,filter);
    }
}
