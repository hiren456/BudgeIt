package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.List;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.database.Database;

import com.codemonkeys9.budgeit.dso.entry.Entry;

class DefaultEntryFetcher implements EntryFetcher {
    Database database;
    EntryListFilterer filter;

    DefaultEntryFetcher(EntryListFilterer filter){
        this.database = DatabaseHolder.getDatabase();
        this.filter = filter;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> list = database.selectByDate(dateInterval);
        this.filter.getIncome(list);

        return list;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> list = database.selectByDate(dateInterval);
        this.filter.getPurchases(list);

        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(dateInterval);

        return list;
    }
}
