package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.codemonkeys9.budgeit.Database.Database;

import com.codemonkeys9.budgeit.Entry.Entry;

class DefaultEntryFetcher implements EntryFetcher {
    Database database;

    DefaultEntryFetcher(Database database){
        this.database = database;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate) {

        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> initialList = database.selectByDate(startDate,endDate);
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = initialList.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount() < 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            initialList.remove(curr);
        }


        return initialList;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(Date startDate, Date endDate) {

        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> initialList = database.selectByDate(startDate,endDate);
        ArrayList<Entry> entriesToRemove = new ArrayList<Entry>();

        Iterator<Entry> iter = initialList.iterator();
        while(iter.hasNext()){

            Entry curr = iter.next();
            if(curr.getAmount() > 0){

                entriesToRemove.add(curr);
            }
        }

        for( Entry curr : entriesToRemove){

            initialList.remove(curr);
        }

        return initialList;
    }

    @Override
    public List<Entry> fetchAllEntrys(Date startDate, Date endDate) {
        // TODO: ensure valid parameters

        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(startDate,endDate);

        // TODO: test the validity of the list
        return list;
    }
}
