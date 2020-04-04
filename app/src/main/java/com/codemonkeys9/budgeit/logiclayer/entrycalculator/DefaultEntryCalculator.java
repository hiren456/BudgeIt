package com.codemonkeys9.budgeit.logiclayer.entrycalculator;


import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

import java.util.Iterator;

class DefaultEntryCalculator implements EntryCalculator {

    @Override
    public Amount sumEntryList(EntryList list) {
        // Sum the amounts of the entrys in the list
        int sum = 0;
        Iterator<Entry> iter = list.getChronoIter();
        while(iter.hasNext()) {
            Entry next = iter.next();

            if( next instanceof Purchase || next instanceof RecurringPurchase){
                sum -= next.getAmount().getValue();
            }
            if( next instanceof Income || next instanceof RecurringIncome){
                sum += next.getAmount().getValue();
            }
        }

        return AmountFactory.fromInt(sum);
    }


}
