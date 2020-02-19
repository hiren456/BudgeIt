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

    // in the future we may want to add order by largest amount
    // and other stuff
    // we could also have methods that return iterators
}
