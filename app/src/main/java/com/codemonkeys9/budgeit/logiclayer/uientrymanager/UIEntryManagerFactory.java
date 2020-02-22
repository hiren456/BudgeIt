package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.entryflagger.EntryFlagger;
import com.codemonkeys9.budgeit.logiclayer.entryflagger.EntryFlaggerFactory;

public class UIEntryManagerFactory {
    public static UIEntryManager createUIEntryManager(){
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFlagger entryFlagger = EntryFlaggerFactory.createEntryFlagger();
        return new DefaultUIEntryManager(entryCreator,entryFlagger);
    }
}
