package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;

public class UIEntryManagerFactory {
    public static UIEntryManager createUIEntryManager(){
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        return new DefaultUIEntryManager(entryCreator,entryFetcher);
    }
}
