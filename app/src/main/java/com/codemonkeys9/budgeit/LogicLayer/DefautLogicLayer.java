package com.codemonkeys9.budgeit.LogicLayer;

import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.Database.Database;
import com.codemonkeys9.budgeit.Database.DatabaseFactory;
import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.EntryCalculator.EntryCalculator;
import com.codemonkeys9.budgeit.LogicLayer.EntryCalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreator;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.LogicLayer.EntryFetcher.EntryFetcher;
import com.codemonkeys9.budgeit.LogicLayer.EntryFetcher.EntryFetcherFactory;

class DefaultLogicLayer implements LogicLayer {

    private EntryFetcher entryFetcher;
    private EntryCalculator entryCalculator;
    private EntryCreator entryCreator;
    private Database database;
    private Date defaultStartDate;


    DefaultLogicLayer(){
        // Create objects using factories
        this.database = new DatabaseFactory().createDatabase(0);
        this.entryCreator = new EntryCreatorFactory().createEntryCreator(this.database);
        this.entryFetcher = new EntryFetcherFactory().createEntryFetcher(this.database);
        this.entryCalculator = new EntryCalculatorFactory().createEntryCalculator();

        // DEFAULT IS THE BEGININIG OF TIME,
        // consider creating a method that allows you to customize this
        this.defaultStartDate = new Date(0);
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate) {
        return this.entryFetcher.fetchAllIncomeEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        return this.entryFetcher.fetchAllIncomeEntrys(this.defaultStartDate, new Date());
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(Date startDate, Date endDate) {
        return this.entryFetcher.fetchAllPurchasesEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllEntrys(Date startDate, Date endDate) {
        return this.entryFetcher.fetchAllEntrys(startDate,endDate);
    }

    @Override
    public int calculateTotalIncome(Date startDate, Date endDate) {
        List<Entry> entryList = fetchAllIncomeEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotalPurchases(Date startDate, Date endDate) {
        List<Entry> entryList = fetchAllPurchaseEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public void createEntry(String amount,  String details, String date) {
        this.entryCreator.createEntry(amount,details,date);
    }
}
