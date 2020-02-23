package com.codemonkeys9.budgeit.entry;

import java.util.Comparator;

// This class is used by stubDatabase to allow it to sort the list
// that it returns by date
public class EntryDateComparator implements Comparator<Entry> {
    public int compare(Entry a, Entry b) {
        return a.getDate().compareTo(b.getDate());
    }
}
