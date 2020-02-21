package com.codemonkeys9.budgeit.logiclayer.entrycalculator;


import java.util.Iterator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

class DefaultEntryCalculator implements EntryCalculator {

    @Override
    public Amount sumEntryList(EntryList list) {
        // Sum the amounts of the entrys in the list
        int sum = 0;
        Iterator<Entry> iter = list.getChronoIter();
        while(iter.hasNext()){

            sum += iter.next().getAmount().getValue();
        }

        return AmountFactory.fromInt(sum);
    }
}
