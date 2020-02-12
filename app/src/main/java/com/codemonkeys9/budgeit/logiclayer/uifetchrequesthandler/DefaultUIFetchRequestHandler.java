package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParser;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrderer;

import java.util.Date;
import java.util.List;

class DefaultUIFetchRequestHandler implements UIFetchRequestHandler {
    DateParser dateParser;
    EntryListOrderer orderer;
    EntryFetcher fetcher;

    DefaultUIFetchRequestHandler(DateParser dateParser, EntryFetcher fetcher,EntryListOrderer orderer){
        this.dateParser = dateParser;
        this.orderer = orderer;
        this.fetcher = fetcher;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        Date parsedEndDate = this.dateParser.parseDate("now");
        Date parsedStartDate = this.dateParser.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        Date parsedEndDate = this.dateParser.parseDate("now");
        Date parsedStartDate = this.dateParser.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        Date parsedEndDate = this.dateParser.parseDate("now");
        Date parsedStartDate = this.dateParser.parseDate("past");

        List<Entry> list = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);

        this.orderer.orderEntryList(list);
        return list;
    }
}
