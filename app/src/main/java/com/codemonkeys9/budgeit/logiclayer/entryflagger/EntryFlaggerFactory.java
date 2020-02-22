package com.codemonkeys9.budgeit.logiclayer.entryflagger;

public class EntryFlaggerFactory {
    public static EntryFlagger createEntryFlagger(){
        return new DefaultEntryFlagger();
    }
}
