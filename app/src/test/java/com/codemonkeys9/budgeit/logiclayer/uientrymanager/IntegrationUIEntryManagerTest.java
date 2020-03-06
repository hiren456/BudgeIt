package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
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
