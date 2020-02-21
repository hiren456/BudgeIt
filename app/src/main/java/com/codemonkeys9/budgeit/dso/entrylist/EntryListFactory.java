package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.List;

public class EntryListFactory {
    public static EntryList fromChrono(List<Entry> list){
       return new LinkedListEntryList(list);
    }
}
