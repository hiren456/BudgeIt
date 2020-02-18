package com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.util.List;

class DefaultUICalculateRequestHandler implements UICalculateRequestHandler {
    ParameterConverter converter;
    EntryFetcher fetcher;
    EntryCalculator calculator;

    DefaultUICalculateRequestHandler(ParameterConverter converter,EntryFetcher fetcher, EntryCalculator calculator){
        this.converter = converter;
        this.calculator = calculator;
        this.fetcher = fetcher;
    }

    @Override
    public Amount calculateTotalIncome(String startDate, String endDate) {
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalIncome() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases(String startDate, String endDate) {
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal(String startDate, String endDate) {
        Date parsedStartDate = DateFactory.fromString(startDate);
        Date parsedEndDate = DateFactory.fromString(endDate);

        List<Entry> entryList = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal() {
        Date parsedStartDate = DateFactory.fromString("past");
        Date parsedEndDate = DateFactory.fromString("now");

        List<Entry> entryList = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }
}
