package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.date.Date;


public class EntryFactory {
    public static Entry createEntry(Amount amount, int entryID, Details details, Date date){
        return new DefaultEntry(amount, entryID, details, date);
    }
}
