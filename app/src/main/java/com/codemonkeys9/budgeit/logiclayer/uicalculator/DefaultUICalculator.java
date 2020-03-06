package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;

class DefaultUICalculator implements UICalculator {
    UIEntryFetcher fetcher;
    EntryCalculator calculator;

    DefaultUICalculator(UIEntryFetcher fetcher, EntryCalculator calculator){
        this.calculator = calculator;
        this.fetcher = fetcher;
    }

    @Override
    public Amount calculateTotalIncome(String startDate, String endDate) {
        EntryList entryList = this.fetcher.fetchAllIncomeEntrys(startDate,endDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalIncome() {
        EntryList entryList = this.fetcher.fetchAllIncomeEntrys();
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases(String startDate, String endDate) {
        EntryList entryList = this.fetcher.fetchAllPurchaseEntrys( startDate,endDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotalPurchases() {
        EntryList entryList = this.fetcher.fetchAllPurchaseEntrys();
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal(String startDate, String endDate) {
        EntryList entryList = this.fetcher.fetchAllEntrys(startDate,endDate);
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }

    @Override
    public Amount calculateTotal() {
        EntryList entryList = this.fetcher.fetchAllEntrys();
        Amount sum = this.calculator.sumEntryList(entryList);

        return sum;
    }
}
