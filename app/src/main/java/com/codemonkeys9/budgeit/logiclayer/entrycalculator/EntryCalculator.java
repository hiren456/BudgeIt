package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

import java.util.List;

import com.codemonkeys9.budgeit.entry.Entry;

public interface EntryCalculator {

    // Takes a list of entries and sums
    // their amount
    int sumEntryList(List<Entry> entryList);

}
