package com.codemonkeys9.budgeit.entry;

import java.time.LocalDate;

public class EntryFactory {
    public static Entry createEntry(int amount, int entryID, String details, LocalDate date){
        return new DefaultEntry(amount, entryID, details, date);
    }
}
