package com.codemonkeys9.budgeit.logiclayer.entrycreator;


import com.codemonkeys9.budgeit.database.Database;

public class EntryCreatorFactory {
    public static EntryCreator createEntryCreator(Database database){
        return new DefaultEntryCreator(database);
    }
}
