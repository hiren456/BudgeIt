package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.LogicLayer.EntryListFilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.LogicLayer.Database.Database;

import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParser;

class DefaultEntryFetcher implements EntryFetcher {
    DateParser dateParser;
    Database database;
    EntryListFilterer filter;
    DefaultEntryFetcher(Database database, DateParser dateParser, EntryListFilterer filter){
        this.database = database;
        this.dateParser = dateParser;
        this.filter = filter;
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date and remove any with negative amounts
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);
        this.filter.getIncome(list);

        // hands list to in reverse chrological order
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllPurchasesEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date and remove any with positive amounts
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);
        this.filter.getPurchases(list);

        // entryFetcher returns entry in reverse chronological order
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        Date parsedEndDate = this.dateParser.parseDate(endDate);
        Date parsedStartDate = this.dateParser.parseDate(startDate);

        // get all entrys within the specified date
        List<Entry> list = database.selectByDate(parsedStartDate,parsedEndDate);

        // entryFetcher returns entry in reverse chronological order
        Collections.reverse(list);
        return list;
    }
}
