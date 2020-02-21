package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

public interface EntryCalculator {

    /*
    Takes an EntryList and returns the sum of the
    entries in that EntryList as an Amount Object
     */
    Amount sumEntryList(EntryList entryList);

}
