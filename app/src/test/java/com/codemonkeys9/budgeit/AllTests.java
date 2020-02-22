package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.database.DatabaseTest;
import com.codemonkeys9.budgeit.dso.date.DateTest;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalTest;
import com.codemonkeys9.budgeit.dso.details.DetailsTest;
import com.codemonkeys9.budgeit.dso.entry.IncomeTest;
import com.codemonkeys9.budgeit.dso.amount.AmountTest;
import com.codemonkeys9.budgeit.dso.entry.PurchaseTest;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListTest;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorTest;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.entryflagger.EntryFlaggerTest;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFiltererTest;
import com.codemonkeys9.budgeit.logiclayer.uicalculator.UIEntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        DatabaseTest.class,
        AmountTest.class,
        DateTest.class,
        DateIntervalTest.class,
        DetailsTest.class,
        IncomeTest.class,
        EntryListTest.class,
        EntryCalculatorTest.class,
        EntryCreatorTest.class,
        EntryFetcherTest.class,
        EntryListFiltererTest.class,
        UIEntryFetcherTest.class,
        UIEntryCalculatorTest.class,
        UIEntryManagerTest.class,
        PurchaseTest.class,
        EntryFlaggerTest.class
})


public class AllTests {

}
