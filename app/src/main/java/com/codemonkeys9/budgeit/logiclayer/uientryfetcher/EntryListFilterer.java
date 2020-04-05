package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class EntryListFilterer {

    public static void getIncome(List<BaseEntry> list) {
        ArrayList<BaseEntry> entriesToRemove = new ArrayList<>();

        Iterator<BaseEntry> iter = list.iterator();
        while(iter.hasNext()){

            BaseEntry curr = iter.next();
            if(curr instanceof Purchase){

                entriesToRemove.add(curr);
            }
        }

        for( BaseEntry curr : entriesToRemove){

            list.remove(curr);
        }
    }

    public static void getPurchases(List<BaseEntry> list) {
        ArrayList<BaseEntry> entriesToRemove = new ArrayList<>();

        Iterator<BaseEntry> iter = list.iterator();
        while(iter.hasNext()){

            BaseEntry curr = iter.next();
            if(curr instanceof Income){

                entriesToRemove.add(curr);
            }
        }

        for( BaseEntry curr : entriesToRemove){

            list.remove(curr);
        }
    }
}
