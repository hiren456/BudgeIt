package com.codemonkeys9.budgeit.database;

import java.util.Comparator;
import com.codemonkeys9.budgeit.entry.Entry;


// This class is used by stubDatabase to allow it to sort the list
// that it returns by date
class EntryDateComparator implements Comparator<Entry> {
    public int compare(Entry a, Entry b) {
        return a.getDate().compareTo(b.getDate());
    }
}
