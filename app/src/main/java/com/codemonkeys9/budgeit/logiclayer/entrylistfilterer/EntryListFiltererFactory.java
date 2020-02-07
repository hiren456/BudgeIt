package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;

public class EntryListFiltererFactory {
    public EntryListFilterer creatEntryListFilterer(){
        return new DefaultEntryListFilterer();
    }
}
