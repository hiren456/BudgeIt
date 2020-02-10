package com.codemonkeys9.budgeit.logiclayer;

import android.util.Pair;

import java.util.List;

import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFiltererFactory;
import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParser;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParserFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;

class DefaultLogicLayer implements LogicLayer {

    private EntryFetcher entryFetcher;
    private EntryCalculator entryCalculator;
    private EntryCreator entryCreator;
    private Database database;


    DefaultLogicLayer(){
        DateParser dateParser = DateParserFactory.createDateParser();
        EntryListFilterer filter = new EntryListFiltererFactory().createEntryListFilterer();
        // Create objects using factories
        this.database = DatabaseFactory.createDatabase(0);
        this.entryCreator = EntryCreatorFactory.createEntryCreator(this.database);
        this.entryFetcher = EntryFetcherFactory.createEntryFetcher(this.database,dateParser,filter);
        this.entryCalculator = EntryCalculatorFactory.createEntryCalculator();
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
    public String calculateTotalIncome(String startDate, String endDate) {
        List<Entry> entryList = fetchAllIncomeEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public String calculateTotalIncome() {
        List<Entry> entryList = fetchAllIncomeEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public String calculateTotalPurchases(String  startDate, String  endDate) {
        List<Entry> entryList = fetchAllPurchaseEntrys(startDate, endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public String calculateTotalPurchases() {
        List<Entry> entryList = fetchAllPurchaseEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public String calculateTotal(String startDate, String endDate) {
        List<Entry> entryList = fetchAllEntrys(startDate,endDate);
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public String calculateTotal() {
        List<Entry> entryList = fetchAllEntrys();
        return this.entryCalculator.sumEntryList(entryList);
    }

    @Override
    public Pair<List<Entry>, String> fetchIncomeDisplayInfo(String startDate, String  endDate) {
        List<Entry> list = fetchAllIncomeEntrys(startDate,endDate);
        String sum = entryCalculator.sumEntryList(list);
        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public Pair<List<Entry>, String> fetchPurchasesDisplayInfo(String  startDate, String  endDate) {
        List<Entry> list = fetchAllPurchaseEntrys(startDate,endDate);
        String sum = entryCalculator.sumEntryList(list);
        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public Pair<List<Entry>, String> fetchAllDisplayInfo(String  startDate, String  endDate) {
        List<Entry> list = fetchAllEntrys(startDate,endDate);
        String sum = entryCalculator.sumEntryList(list);
        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public Pair<List<Entry>,String> fetchIncomeDisplayInfo() {
        List<Entry> list = fetchAllIncomeEntrys();
        String sum = entryCalculator.sumEntryList(list);
        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public Pair<List<Entry>, String> fetchPurchasesDisplayInfo() {
        List<Entry> list = fetchAllPurchaseEntrys();
        String sum = entryCalculator.sumEntryList(list);

        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public Pair<List<Entry>, String> fetchAllDisplayInfo() {
        List<Entry> list = fetchAllEntrys();
        String sum = entryCalculator.sumEntryList(list);

        return new Pair<List<Entry>,String>(list,sum);
    }

    @Override
    public void createEntry(String amount,  String details, String date) {
        this.entryCreator.createEntry(amount,details,date);
    }
}
