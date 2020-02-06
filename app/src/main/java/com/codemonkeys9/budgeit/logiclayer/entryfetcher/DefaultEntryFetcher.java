package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrderer;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrdererFactor;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.database.Database;

import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParser;

class DefaultEntryFetcher implements EntryFetcher {
    DateParser dateParser;
    Database database;
    EntryListFilterer filter;
    EntryListOrderer orderer;
    DefaultEntryFetcher(Database database, DateParser dateParser, EntryListFilterer filter){
        this.database = database;
        this.dateParser = dateParser;
        this.filter = filter;
        this.orderer = new EntryListOrdererFactor().createEntryListOrderer();
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);
        this.filter.getIncome(list);

        orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);
        this.filter.getPurchases(list);

        orderer.orderEntryList(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);

        orderer.orderEntryList(list);
        return list;
    }
}
