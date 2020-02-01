package com.codemonkeys9.budgeit.LogicLayer.EntryCalculator;


import java.util.Iterator;
import java.util.List;

import com.codemonkeys9.budgeit.Entry.Entry;

class DefaultEntryCalculator implements EntryCalculator {

    @Override
    public int sumEntryList(List<Entry> entryList) {
        // TODO: ensure validity of entryList

        // Sum the amounts of the entrys in the list
        int sum = 0;
        Iterator<Entry> iter = entryList.iterator();
        while(iter.hasNext()){

            sum += iter.next().getAmount();
        }

        // TODO: test that sum is valid
        return sum;
    }
}
