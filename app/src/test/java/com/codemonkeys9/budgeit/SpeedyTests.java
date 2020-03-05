package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.database.DatabaseTest;
import com.codemonkeys9.budgeit.database.StubDatabaseTest;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryTest;
import com.codemonkeys9.budgeit.dso.category.SavingCategoryTest;
import com.codemonkeys9.budgeit.dso.date.DateTest;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalTest;
import com.codemonkeys9.budgeit.dso.details.DetailsTest;
import com.codemonkeys9.budgeit.dso.entry.IncomeTest;
import com.codemonkeys9.budgeit.dso.amount.AmountTest;
import com.codemonkeys9.budgeit.dso.entry.PurchaseTest;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListTest;
import com.codemonkeys9.budgeit.integrationtests.IntegrationTests;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorTest;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.entryflagger.EntryFlaggerTest;
import com.codemonkeys9.budgeit.logiclayer.entrylistfilterer.EntryListFiltererTest;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerTest;
import com.codemonkeys9.budgeit.logiclayer.uicalculator.UIEntryCalculatorTest;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorTest;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifier;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifierTest;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerTest;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherTest;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        StubDatabaseTest.class,
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
        EntryFlaggerTest.class,
        IDManagerTest.class,
        SavingCategoryTest.class,
        BudgetCategoryTest.class,
        UICategoryCreatorTest.class,
        UICategoryFetcherTest.class,
        UIEntryCategorizerTest.class,
        UICategoryModifierTest.class,
})

// All the tests that only use the Stub and thus are speedy
public class SpeedyTests {

}
