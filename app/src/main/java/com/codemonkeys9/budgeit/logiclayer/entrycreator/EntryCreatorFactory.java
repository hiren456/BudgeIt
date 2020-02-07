package com.codemonkeys9.budgeit.logiclayer.entrycreator;


import com.codemonkeys9.budgeit.database.Database;

public class EntryCreatorFactory {
    public EntryCreator createEntryCreator(Database database){
        return new DefaultEntryCreator(database);
    }
}
