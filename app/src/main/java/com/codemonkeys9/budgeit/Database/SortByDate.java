package com.codemonkeys9.budgeit.Database;

import java.util.Comparator;

import com.codemonkeys9.budgeit.Entry.Entry;


// This class is used by stubDatabase to allow it to sort the list
// that it returns by date
class SortByDate implements Comparator<Entry> {
    public int compare(Entry a, Entry b) {
        return a.getDate().compareTo(b.getDate());
    }
}
