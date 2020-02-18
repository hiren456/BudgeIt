package com.codemonkeys9.budgeit.logiclayer.entrycalculator;


import java.util.Iterator;
import java.util.List;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;

class DefaultEntryCalculator implements EntryCalculator {

    @Override
    public Amount sumEntryList(List<Entry> entryList) {
        // Sum the amounts of the entrys in the list
        int sum = 0;
        Iterator<Entry> iter = entryList.iterator();
        while(iter.hasNext()){

            sum += iter.next().getAmount().getValue();
        }

        return AmountFactory.fromInt(sum);
    }
}
