package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class LinkedListEntryList implements EntryList {

    List<BaseEntry> chronoList;

    LinkedListEntryList(List<BaseEntry> list) {
        this.chronoList = list;
    }

    @Override
    public List<BaseEntry> getChrono() {
        List<BaseEntry> copy = new LinkedList<>(this.chronoList);

        return copy;
    }

    @Override
    public Iterator<BaseEntry> getChronoIter() {
        List<BaseEntry> copy = new LinkedList<>(this.chronoList);

        return copy.iterator();
    }

    @Override
    public List<BaseEntry> getReverseChrono() {
        List<BaseEntry> copy = new LinkedList<>(this.chronoList);

        Collections.reverse(copy);

        return copy;
    }

    @Override
    public Iterator<BaseEntry> getReverseChronoIter() {
        List<BaseEntry> copy = new LinkedList<>(this.chronoList);

        Collections.reverse(copy);

        return copy.iterator();
    }

    @Override
    public int getReverseChronoIndexOfEntryWithID(int entryID) {
        List<BaseEntry> list = this.getReverseChrono();
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
