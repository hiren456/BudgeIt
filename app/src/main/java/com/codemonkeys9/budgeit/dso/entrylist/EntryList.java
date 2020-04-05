package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;

import java.util.Iterator;
import java.util.List;

public interface EntryList{

    /*
    returns the list of entries in chronological order
     */
    List<BaseEntry> getChrono();
    Iterator<BaseEntry> getChronoIter();

    /*
    returns the list of entries in reverse chronological order
     */
    List<BaseEntry> getReverseChrono();
    Iterator<BaseEntry> getReverseChronoIter();

    /*
    Returns index in a reverse chronologically-ordered list of the entry with the desired ID
     */
    int getReverseChronoIndexOfEntryWithID(int entryID);

    /*
    returns the size of the list
     */
    int size();
}
