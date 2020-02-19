package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

public interface EntryCalculator {

    // Takes a list of entries and sums
    // their amount
    Amount sumEntryList(EntryList entryList);

}
