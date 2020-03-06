package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class IntegrationUICategoryModifierTest extends UICategoryModifierTest {
    Database db;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createTestableDatabase(
                context,idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
        db = DatabaseHolder.getDatabase();
        Amount goal = AmountFactory.fromString( "200.00");
        Details name = DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);

        Amount goal2 = AmountFactory.fromString( "7000.00");
        Details name2 = DetailsFactory.fromString( "Phone");
        Date date2 = DateFactory.fromInts(1999,04,23);
        int catID2 = 25;
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name2,goal2,date2,catID2);
        db.insertCategory(cat2);
    }

    @After
    public void closeDb() throws IOException {
        db.clean();
        db.close();
    }
}
