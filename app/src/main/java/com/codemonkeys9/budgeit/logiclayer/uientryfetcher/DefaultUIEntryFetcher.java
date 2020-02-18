package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
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
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }
}
