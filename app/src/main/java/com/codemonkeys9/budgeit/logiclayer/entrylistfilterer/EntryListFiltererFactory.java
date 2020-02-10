package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;

public class EntryListFiltererFactory {
    public static EntryListFilterer createEntryListFilterer(){
        return new DefaultEntryListFilterer();
    }
}
