package com.codemonkeys9.budgeit.LogicLayer.EntryCreator;


import com.codemonkeys9.budgeit.Database.Database;

public class EntryCreatorFactory {
    public EntryCreator createEntryCreator(Database database){
        return new DefaultEntryCreator(database);
    }
}
