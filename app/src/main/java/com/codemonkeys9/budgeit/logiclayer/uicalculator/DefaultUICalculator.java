package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.dateintervel.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

import java.util.List;

class DefaultUICalculator implements UICalculator {
    ParameterConverter converter;
    EntryFetcher fetcher;
    EntryCalculator calculator;

    DefaultUICalculator(ParameterConverter converter, EntryFetcher fetcher, EntryCalculator calculator){
        this.converter = converter;
        this.calculator = calculator;
        this.fetcher = fetcher;
    }

    @Override
    public Amount calculateTotalIncome(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalIncome() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> entryList = this.fetcher.fetchAllIncomeEntrys(dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> entryList = this.fetcher.fetchAllPurchasesEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        List<Entry> entryList = this.fetcher.fetchAllEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        List<Entry> entryList = this.fetcher.fetchAllEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }
}
