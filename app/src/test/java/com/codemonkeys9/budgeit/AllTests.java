package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.database.DatabaseTest;
import com.codemonkeys9.budgeit.entry.EntryTest;
import com.codemonkeys9.budgeit.logiclayer.LogicLayerTest;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorTest;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherTest;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        DatabaseTest.class,
        EntryTest.class,
        LogicLayerTest.class,
        EntryCalculatorTest.class,
        EntryCreatorTest.class,
        EntryFetcherTest.class
})


public class AllTests {

}
