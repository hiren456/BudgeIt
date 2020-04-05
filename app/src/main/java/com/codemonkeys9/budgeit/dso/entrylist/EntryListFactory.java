package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;

import java.util.List;

public class EntryListFactory {
    public static EntryList fromChrono(List<BaseEntry> list){
       return new LinkedListEntryList(list);
    }
}
