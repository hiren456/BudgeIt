package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.util.Date;
import java.util.List;

class DefaultUIFetchRequestHandler implements UIFetchRequestHandler {
    ParameterConverter converter;
    EntryFetcher fetcher;

    DefaultUIFetchRequestHandler(ParameterConverter converter, EntryFetcher fetcher){
        this.converter = converter;
        this.fetcher = fetcher;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.converter.parseDate(endDate);
        Date parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.converter.parseDate(endDate);
        Date parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.converter.parseDate(endDate);
        Date parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        Date parsedEndDate = this.converter.parseDate("now");
        Date parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        Date parsedEndDate = this.converter.parseDate("now");
        Date parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        Date parsedEndDate = this.converter.parseDate("now");
        Date parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }
}
