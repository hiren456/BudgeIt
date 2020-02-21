package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class DefaultEntryListFilterer implements EntryListFilterer {

    @Override
    public void getIncome(List<Entry> list) {
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = list.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount().getValue() < 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            list.remove(curr);
        }
    }

    @Override
    public void getPurchases(List<Entry> list) {
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = list.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount().getValue() > 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            list.remove(curr);
        }
    }
}
