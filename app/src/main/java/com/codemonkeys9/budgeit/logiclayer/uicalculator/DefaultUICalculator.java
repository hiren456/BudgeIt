package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.dateintervel.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;

class DefaultUICalculator implements UICalculator {
    EntryFetcher fetcher;
    EntryCalculator calculator;

    DefaultUICalculator(EntryFetcher fetcher, EntryCalculator calculator){
        this.calculator = calculator;
        this.fetcher = fetcher;
    }

    @Override
    public Amount calculateTotalIncome(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList entryList = this.fetcher.fetchAllIncomeEntrys(dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalIncome() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList entryList = this.fetcher.fetchAllIncomeEntrys(dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList entryList = this.fetcher.fetchAllPurchasesEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList entryList = this.fetcher.fetchAllPurchasesEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal(String startDate, String endDate) {
        DateInterval dateInterval = DateIntervalFactory.fromString(startDate,endDate);

        EntryList entryList = this.fetcher.fetchAllEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal() {
        DateInterval dateInterval = DateIntervalFactory.fromString("past","now");

        EntryList entryList = this.fetcher.fetchAllEntrys( dateInterval);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }
}
