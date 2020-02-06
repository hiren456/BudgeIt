package com.codemonkeys9.budgeit.LogicLayer.EntryCalculator;

import java.util.List;

import com.codemonkeys9.budgeit.Entry.Entry;

public interface EntryCalculator {

    // Takes a list of entries and sums
    // their amount
    String sumEntryList(List<Entry> entryList);

}
