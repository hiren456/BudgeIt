package com.codemonkeys9.budgeit.logiclayer.entrycreator;

public class EntryCreatorFactory {
    public static EntryCreator createEntryCreator(){
        return new DefaultEntryCreator();
    }
}
