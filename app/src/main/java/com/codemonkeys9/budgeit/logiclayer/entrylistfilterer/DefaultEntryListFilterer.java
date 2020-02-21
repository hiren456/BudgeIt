package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

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
            if(curr instanceof Purchase){

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
            if(curr instanceof Income){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            list.remove(curr);
        }
    }
}
