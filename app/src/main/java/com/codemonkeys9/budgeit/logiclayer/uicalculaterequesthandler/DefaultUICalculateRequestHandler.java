package com.codemonkeys9.budgeit.logiclayer.uicalculaterequesthandler;

import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.time.LocalDate;
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
    public String calculateTotalIncome(String startDate, String endDate) {
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum = this.converter.createDisplayAmount(sum);
        return displaySum;
    }

    @Override
    public String calculateTotalIncome() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");


        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum = this.converter.createDisplayAmount(sum);
        return displaySum;
    }

    @Override
    public String calculateTotalPurchases(String startDate, String endDate) {
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum = this.converter.createDisplayAmount(sum);
        return displaySum;
    }

    @Override
    public String calculateTotalPurchases() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum  = this.converter.createDisplayAmount(sum);
        return displaySum;
    }

    @Override
    public String calculateTotal(String startDate, String endDate) {
        LocalDate parsedEndDate = this.converter.parseDate(endDate);
        LocalDate parsedStartDate = this.converter.parseDate(startDate);

        List<Entry> entryList = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum = this.converter.createDisplayAmount(sum);
        return displaySum;
    }

    @Override
    public String calculateTotal() {
        LocalDate parsedEndDate = this.converter.parseDate("now");
        LocalDate parsedStartDate = this.converter.parseDate("past");

        List<Entry> entryList = this.fetcher.fetchAllEntrys(parsedStartDate,parsedEndDate);
        int sum = this.calculator.sumEntryList(entryList);

        String displaySum = this.converter.createDisplayAmount(sum);
        return displaySum;
    }
}
