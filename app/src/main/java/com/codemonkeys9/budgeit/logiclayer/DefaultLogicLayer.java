package com.codemonkeys9.budgeit.logiclayer;

import android.util.Pair;

import java.util.List;

import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrderer;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer.EntryListOrdererFactory;
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
import com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler.UIFetchRequestHandler;
import com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler.UIFetchRequestHandlerFactory;

class DefaultLogicLayer implements LogicLayer {

    // Idealy only ui handlers
    UIFetchRequestHandler uiFetchRequestHandler;

    EntryCalculator entryCalculator;
    EntryCreator entryCreator;


    DefaultLogicLayer(){
        DateParser dateParser = DateParserFactory.createDateParser();
        EntryListFilterer filter = EntryListFiltererFactory.createEntryListFilterer();
        Database database = DatabaseFactory.createDatabase(0);
        EntryListOrderer orderer = EntryListOrdererFactory.createEntryListOrderer();

        EntryFetcher fetcher = EntryFetcherFactory.createEntryFetcher(database,filter);

        this.entryCalculator = EntryCalculatorFactory.createEntryCalculator();
        this.entryCreator = EntryCreatorFactory.createEntryCreator(database);
        this.uiFetchRequestHandler = UIFetchRequestHandlerFactory.createUIFetchRequestHandler(dateParser, fetcher, orderer);
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys(String startDate, String endDate) {
        return this.uiFetchRequestHandler.fetchAllIncomeEntrys(startDate,endDate);
    }

    @Override
    public List<Entry> fetchAllIncomeEntrys() {
        return this.uiFetchRequestHandler.fetchAllIncomeEntrys();
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate) {
        return this.uiFetchRequestHandler.fetchAllPurchaseEntrys(startDate, endDate);
    }

    @Override
    public List<Entry> fetchAllPurchaseEntrys() {
        return this.uiFetchRequestHandler.fetchAllPurchaseEntrys();
    }

    @Override
    public List<Entry> fetchAllEntrys(String startDate, String endDate) {
        return this.uiFetchRequestHandler.fetchAllEntrys(startDate,endDate);
    }

    @Override
    public List<Entry> fetchAllEntrys() {
        return this.uiFetchRequestHandler.fetchAllEntrys();
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
