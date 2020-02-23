package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.time.LocalDate;
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
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        List<Entry> displayList = this.converter.createDisplayEntryList(list);
        return displayList;
    }
}
