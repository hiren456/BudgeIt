package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.List;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListFactory;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
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
    public EntryList fetchAllIncomeEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> list = database.selectByDate(dateInterval);
        this.filter.getIncome(list);

        EntryList entryList = EntryListFactory.fromChrono(list);
        return entryList;
    }

    @Override
    public EntryList fetchAllPurchasesEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> list = database.selectByDate(dateInterval);
        this.filter.getPurchases(list);

        EntryList entryList = EntryListFactory.fromChrono(list);
        return entryList;
    }

    @Override
    public EntryList fetchAllEntrys(DateInterval dateInterval) {
        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(dateInterval);

        EntryList entryList = EntryListFactory.fromChrono(list);
        return entryList;
    }
}
