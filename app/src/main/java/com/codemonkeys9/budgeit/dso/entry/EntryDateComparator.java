package com.codemonkeys9.budgeit.dso.entry;

import java.util.Comparator;


// This class is used by stubDatabase to allow it to sort the list
// that it returns by date
public class EntryDateComparator implements Comparator<BaseEntry> {
    public int compare(BaseEntry a, BaseEntry b) {
        return a.getDate().compareTo(b.getDate());
    }
}
