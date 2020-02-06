package com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer;

public class EntryListOrdererFactor {
    public EntryListOrderer createEntryListOrderer(){
        return new DefaultEntryListOrderer();
    }
}
