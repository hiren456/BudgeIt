package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;

public class UIEntryManagerFactory {
    public static UIEntryManager createUIEntryManager(){
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        return new DefaultUIEntryManager(entryCreator);
    }
}
