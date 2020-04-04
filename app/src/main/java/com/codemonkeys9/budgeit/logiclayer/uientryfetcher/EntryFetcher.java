package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListFactory;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;

import java.util.List;

class EntryFetcher implements UIEntryFetcher {
    Database db;
    EntryFetcher(){
        this.db = DatabaseHolder.getDatabase();
    }

    @Override
    public EntryList fetchAllIncomeEntrys(String startDate, String endDate) {
        DateInterval dateInterval = parseInterval(startDate,endDate);
        List<Entry> list = fetchList(dateInterval);
        EntryListFilterer.getIncome(list);
        return parseList(list);
    }

    @Override
    public EntryList fetchAllPurchaseEntrys(String startDate, String endDate) {
        DateInterval dateInterval = parseInterval(startDate,endDate);
        List<Entry> list = fetchList(dateInterval);
        EntryListFilterer.getPurchases(list);
        return parseList(list);
    }

    @Override
    public EntryList fetchAllEntrys(String startDate, String endDate) {
        DateInterval dateInterval = parseInterval(startDate,endDate);
        List<Entry> list = fetchList(dateInterval);
        return parseList(list);
    }

    @Override
    public EntryList fetchAllIncomeEntrys() {
        DateInterval dateInterval = parseInterval("past","now");
        List<Entry> list = fetchList(dateInterval);
        EntryListFilterer.getIncome(list);
        return parseList(list);
    }

    @Override
    public EntryList fetchAllPurchaseEntrys() {
        DateInterval dateInterval = parseInterval("past","now");
        List<Entry> list = fetchList(dateInterval);
        EntryListFilterer.getPurchases(list);
        return parseList(list);
    }

    @Override
    public EntryList fetchAllEntrys() {
        DateInterval dateInterval = parseInterval("past","now");
        List<Entry> list = fetchList(dateInterval);

        return parseList(list);
    }

    @Override
    public EntryList fetchEntrysInCategory(int catID) throws CategoryDoesNotExistException {
        List<Entry> list = db.getDefaultEntriesByCategoryID(catID);
        if(list == null){
            throw new CategoryDoesNotExistException("Category with id: " + catID+ " does not exist.");
        }
        return parseList(list);
    }
    @Override
    public EntryList fetchEntrysInCategoryThisMonth(int catID) throws CategoryDoesNotExistException {
        Date now = DateFactory.fromString("now");
        Date firstOfMonth = DateFactory.fromInts(now.getYear(), now.getMonth(), 1);
        DateInterval dateInterval = DateIntervalFactory.fromDate(firstOfMonth, now);
        List<Entry> list = db.selectDefaultEntriesByDateAndCategoryID(dateInterval, catID);
        if(list == null){
            throw new CategoryDoesNotExistException("Category with id: " + catID+ " does not exist.");
        }
        return parseList(list);
    }

    @Override
    public Entry fetchEntryByID(int entryID) throws EntryDoesNotExistException {
        Entry entry = db.selectDefaultEntryByID(entryID);
        if(entry == null){
            throw new EntryDoesNotExistException("Entry with id: " + entryID+" does not exist.");
        }
        return entry;
    }


    private DateInterval parseInterval(String start,String end){
        DateInterval dateInterval = DateIntervalFactory.fromString(start,end);
        return dateInterval;
    }
    private EntryList parseList(List<Entry> list){
        return EntryListFactory.fromChrono(list);
    }
    private List<Entry> fetchList(DateInterval dateInterval){
        return db.selectDefaultEntriesByDate(dateInterval);
    }

}
