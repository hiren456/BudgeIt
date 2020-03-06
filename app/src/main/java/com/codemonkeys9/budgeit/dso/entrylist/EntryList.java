package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Iterator;
import java.util.List;

public interface EntryList{

    /*
    returns the list of entrys in chronological order
     */
    List<Entry> getChrono();
    Iterator<Entry> getChronoIter();

    /*
    returns the list of entrys in chronological order
     */
    List<Entry> getReverseChrono();
    Iterator<Entry> getReverseChronoIter();

    /*
    returns the size of the list
     */
    int size();
}
