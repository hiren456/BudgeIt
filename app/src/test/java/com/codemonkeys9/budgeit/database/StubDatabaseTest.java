package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriodFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncomeFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StubDatabaseTest extends DatabaseTest{
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
        this.db = DatabaseHolder.getDatabase();
    }

    @Override
    @Test
    public void updateDateLastCheckedForCategoryTest(){
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }

    @Override
    @Test
    public void updateDateLastCheckedForRecurringTest(){
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }

    @Override
    @Test
    public void initialDateLastCheckedTest(){
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }
}
