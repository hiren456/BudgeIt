package com.codemonkeys9.budgeit.logiclayer;

import android.util.Pair;

import java.util.List;

import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverterFactory;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFilterer;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFiltererFactory;
import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler.UICalculateRequestHandler;
import com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler.UICalculaterRequestHandlerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler.UIEntryModificationRequestHandler;
import com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler.UIEntryModificationRequestHandlerFactory;
import com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler.UIFetchRequestHandler;
import com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler.UIFetchRequestHandlerFactory;

class DefaultLogicLayer implements LogicLayer {

    // Idealy only ui handlers
    UIFetchRequestHandler uiFetchRequestHandler;
    UICalculateRequestHandler uiCalculateRequestHandler;
    UIEntryModificationRequestHandler uiEntryModificationHandler;

    DefaultLogicLayer(){
        ParameterConverter converter = ParameterConverterFactory.createParameterConverter();
        EntryListFilterer filter = EntryListFiltererFactory.createEntryListFilterer();
        Database database = DatabaseFactory.createDatabase(0);
        EntryCalculator calculator = EntryCalculatorFactory.createEntryCalculator();

        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        EntryFetcher fetcher = EntryFetcherFactory.createEntryFetcher(database,filter);

        this.uiFetchRequestHandler = UIFetchRequestHandlerFactory.
                createUIFetchRequestHandler(converter, fetcher);
        this.uiCalculateRequestHandler = UICalculaterRequestHandlerFactory.
                createUICalculateRequestHandler(converter,fetcher,calculator);
        this.uiEntryModificationHandler = UIEntryModificationRequestHandlerFactory.
                createUIEntryModificationRequestHandler(converter,entryCreator);
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
        return this.uiCalculateRequestHandler.calculateTotalIncome(startDate,endDate);
    }

    @Override
    public String calculateTotalIncome() {
        return this.uiCalculateRequestHandler.calculateTotalIncome();
    }

    @Override
    public String calculateTotalPurchases(String  startDate, String  endDate) {
        return this.uiCalculateRequestHandler.calculateTotalPurchases(startDate,endDate);
    }

    @Override
    public String calculateTotalPurchases() {
        return this.uiCalculateRequestHandler.calculateTotalPurchases();
    }

    @Override
    public String calculateTotal(String startDate, String endDate) {
        return this.uiCalculateRequestHandler.calculateTotal(startDate,endDate);
    }

    @Override
    public String calculateTotal() {
        return this.uiCalculateRequestHandler.calculateTotal();
    }

    @Override
    public Pair<List<Entry>, String> fetchIncomeDisplayInfo(String startDate, String  endDate) {
        return null;
    }

    @Override
    public Pair<List<Entry>, String> fetchPurchasesDisplayInfo(String  startDate, String  endDate) {
        return null;
    }

    @Override
    public Pair<List<Entry>, String> fetchAllDisplayInfo(String  startDate, String  endDate) {
        return null;
    }

    @Override
    public Pair<List<Entry>,String> fetchIncomeDisplayInfo() {
        return null;
    }

    @Override
    public Pair<List<Entry>, String> fetchPurchasesDisplayInfo() {
        return null;
    }

    @Override
    public Pair<List<Entry>, String> fetchAllDisplayInfo() {
        return null;
    }

    @Override
    public void createEntry(String amount,  String details, String date) {
        this.uiEntryModificationHandler.createEntry(amount,details,date);
    }
}
