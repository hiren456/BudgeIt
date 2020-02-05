package com.codemonkeys9.budgeit.LogicLayer;

import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.LogicLayer.Database.Database;
import com.codemonkeys9.budgeit.LogicLayer.Database.DatabaseFactory;
import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParser;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParserFactory;
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


    DefaultLogicLayer(){
        DateParser dateParser = new DateParserFactory().createDateParser();

        // Create objects using factories
        this.database = new DatabaseFactory().createDatabase(0);
        this.entryCreator = new EntryCreatorFactory().createEntryCreator(this.database);
        this.entryFetcher = new EntryFetcherFactory().createEntryFetcher(this.database,dateParser);
        this.entryCalculator = new EntryCalculatorFactory().createEntryCalculator();
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        // startDate and endDate are expected to be in "dd/mm/yyyy" format
        // or "past", "now"
        return this.entryFetcher.fetchAllIncomeEntrys(startDate,endDate);
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        return this.entryFetcher.fetchAllIncomeEntrys("past","now");
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        return this.entryFetcher.fetchAllPurchasesEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        return this.entryFetcher.fetchAllPurchasesEntrys("past", "now");
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        return this.entryFetcher.fetchAllEntrys(startDate,endDate);
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        return this.entryFetcher.fetchAllEntrys("past","now");
    }

    @Override
    public int calculateTotalIncome(String startDate, String endDate) {
        List<Entry> entryList = fetchAllIncomeEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotalIncome() {
        List<Entry> entryList = fetchAllIncomeEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotalPurchases(String  startDate, String  endDate) {
        List<Entry> entryList = fetchAllPurchaseEntrys(startDate, endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotalPurchases() {
        List<Entry> entryList = fetchAllPurchaseEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotal(String startDate, String endDate) {
        List<Entry> entryList = fetchAllEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public int calculateTotal() {
        List<Entry> entryList = fetchAllEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchIncomeDisplayInfo(String startDate, String  endDate) {
        List<Entry> list = fetchAllIncomeEntrys(startDate,endDate);
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchPurchasesDisplayInfo(String  startDate, String  endDate) {
        List<Entry> list = fetchAllPurchaseEntrys(startDate,endDate);
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchAllDisplayInfo(String  startDate, String  endDate) {
        List<Entry> list = fetchAllEntrys(startDate,endDate);
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchIncomeDisplayInfo() {
        List<Entry> list = fetchAllIncomeEntrys();
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchPurchasesDisplayInfo() {
        List<Entry> list = fetchAllPurchaseEntrys();
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public Pair<List<Entry>, Integer> fetchAllDisplayInfo() {
        List<Entry> list = fetchAllEntrys();
        int sum = entryCalculator.sumEntryList(list);

        // entryFetcher returns entry in chronological order
        // this method needs to return a list in reverse chronological order
        // this code makes that change
        Collections.reverse(list);
        return new Pair<List<Entry>,Integer>(list,sum);
    }

    @Override
    public void createEntry(String amount,  String details, String date) {
        this.entryCreator.createEntry(amount,details,date);
    }
}
