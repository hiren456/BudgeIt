package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.database.Database;

import com.codemonkeys9.budgeit.entry.Entry;

class DefaultEntryFetcher implements EntryFetcher {
    Database database;
    EntryListFilterer filter;

    DefaultEntryFetcher(Database database, EntryListFilterer filter){
        this.database = database;
        this.filter = filter;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate) {
        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> list = database.selectByDate(startDate,endDate);
        this.filter.getIncome(list);

        return list;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(Date startDate, Date endDate) {
        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> list = database.selectByDate(startDate,endDate);
        this.filter.getPurchases(list);

        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys(Date startDate, Date endDate) {
        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(startDate,endDate);

        return list;
    }
}
