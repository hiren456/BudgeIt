package com.codemonkeys9.budgeit.database;

import android.app.Application;
import android.content.Context;

import com.codemonkeys9.budgeit.BuildConfig;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RDTestSample {
    private RealDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new RealDatabase(context);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertTest() {
        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 42;
        int catID1 = 20;
        Details details1 = DetailsFactory.fromString("Tutor");
        Date date1 = DateFactory.fromInts(2016, 7, 7);

        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1,catID1);

        //Create valid Category
        Amount goal = AmountFactory.fromInt(100);
        Details catName = DetailsFactory.fromString("Tutor");
        Date catDate = DateFactory.fromInts(2014, 7, 7);

        Category cat1 = BudgetCategoryFactory.createBudgetCategory(catName, goal, catID1);
        db.insertCategory(cat1);
        db.insertEntry(entry1);

        Entry retEntry1 = db.selectByID(entryID1);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return an entry using selecBYID",retEntry1);
        assertTrue("Database returns a entry with the wrong amount using selectByID"
                ,retEntry1.getAmount().equals(amount1));



    }
}
