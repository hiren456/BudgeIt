package com.codemonkeys9.budgeit.dso.entry;

import java.util.Date;

public class EntryFactory {
    public static Entry createEntry(int amount, int entryID, String details, Date date){
        return new DefaultEntry(amount, entryID, details, date);
    }
}
