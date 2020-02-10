package com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer;

public class EntryListOrdererFactory {
    public static EntryListOrderer createEntryListOrderer(){
        return new DefaultEntryListOrderer();
    }
}
