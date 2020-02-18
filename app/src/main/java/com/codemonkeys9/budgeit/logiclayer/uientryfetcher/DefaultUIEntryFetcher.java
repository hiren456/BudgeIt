package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.dateintervel.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.util.List;

class DefaultUIEntryFetcher implements UIEntryFetcher {
    ParameterConverter converter;
    EntryFetcher fetcher;

    DefaultUIEntryFetcher(ParameterConverter converter, EntryFetcher fetcher){
        this.converter = converter;
        this.fetcher = fetcher;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> list = this.fetcher.fetchAllEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> list = this.fetcher.fetchAllEntrys(dateInterval);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }
}
