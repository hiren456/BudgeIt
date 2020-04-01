package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class LinkedListEntryList implements EntryList {

    List<Entry> chronoList;

    LinkedListEntryList(List<Entry> list) {
        this.chronoList = list;
    }

    @Override
    public List<Entry> getChrono() {
        List<Entry> copy = new LinkedList<Entry>(this.chronoList);

        return copy;
    }

    @Override
    public Iterator<Entry> getChronoIter() {
        List<Entry> copy = new LinkedList<Entry>(this.chronoList);

        return copy.iterator();
    }

    @Override
    public List<Entry> getReverseChrono() {
        List<Entry> copy = new LinkedList<Entry>(this.chronoList);

        Collections.reverse(copy);

        return copy;
    }

    @Override
    public Iterator<Entry> getReverseChronoIter() {
        List<Entry> copy = new LinkedList<Entry>(this.chronoList);

        Collections.reverse(copy);

        return copy.iterator();
    }

    @Override
    public int getReverseChronoIndexOfEntryWithID(int entryID) {
        List<Entry> list = this.getReverseChrono();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getEntryID() == entryID) {
                return i;
            }
        }

        // If we get to this point, `entryID` is not in the list
        throw new IllegalArgumentException("Entry with ID " + entryID + " is not in the list");
    }

    @Override
    public int size() {
        return this.chronoList.size();
    }
}
