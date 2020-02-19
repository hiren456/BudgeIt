package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.dateintervel.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.util.List;

class DefaultUIEntryFetcher implements UIEntryFetcher {
    EntryFetcher fetcher;

    DefaultUIEntryFetcher(EntryFetcher fetcher){
        this.fetcher = fetcher;
    }

    @Override
    public EntryList fetchAllIncomeEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList list = this.fetcher.fetchAllIncomeEntrys(dateInterval);

        return list;
    }

    @Override
    public EntryList fetchAllPurchaseEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList list = this.fetcher.fetchAllPurchasesEntrys(dateInterval);

        return list;
    }

    @Override
    public EntryList fetchAllEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList list = this.fetcher.fetchAllEntrys(dateInterval);

        return list;
    }

    @Override
    public EntryList fetchAllIncomeEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList list = this.fetcher.fetchAllIncomeEntrys(dateInterval);

        return list;
    }

    @Override
    public EntryList fetchAllPurchaseEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList list = this.fetcher.fetchAllPurchasesEntrys(dateInterval);

        return list;
    }

    @Override
    public EntryList fetchAllEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList list = this.fetcher.fetchAllEntrys(dateInterval);

        return list;
    }
}
