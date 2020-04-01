package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IntegrationUIEntryManagerTest extends UIEntryManagerTest {
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
        int entryID = 24;
        Entry entry = PurchaseFactory.createPurchase(goal,entryID,name,date,idManager.getDefaultID("Category"));
        db.insertEntry(entry);

        Amount goal2 = AmountFactory.fromString( "7000.00");
        Details name2 = DetailsFactory.fromString( "Phone");
        Date date2 = DateFactory.fromInts(1999,04,23);
        int entryID2 = 25;
        Entry entry2 = IncomeFactory.createIncome(goal,entryID2,name,date,idManager.getDefaultID("Category"));
        db.insertEntry(entry2);
    }

    @After
    public void closeDb() throws IOException {
        db.clean();
        db.close();
    }
    @Test
    public void flagFlaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase);

        Purchase entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);
        manager.flagPurchase(entry,true);
        entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);
        manager.flagPurchase(entry,true);
        entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
        assertTrue(entry.flagged());


    }
}
