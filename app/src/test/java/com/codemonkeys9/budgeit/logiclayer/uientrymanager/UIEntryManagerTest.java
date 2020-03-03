package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import android.provider.ContactsContract;

import com.codemonkeys9.budgeit.database.Database;
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
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UIEntryManagerTest {
    @Before
    public void resetDatabase() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = DatabaseHolder.class.getDeclaredField("db");
        instance.setAccessible(true);
        instance.set(null, null);
        DatabaseHolder.init();
    }

    @Test
    public void deleteValidEntry(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        int catID = 20;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date,catID);

        Database db = DatabaseHolder.getDatabase();
        db.insertEntry(entry);


        manager.deleteEntry(42);
        assertNull(db.selectByID(42));
    }

    @Test
    public void deleteInValidEntry(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        int catID = 20;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date,catID);

        Database db = DatabaseHolder.getDatabase();
        db.insertEntry(entry);


        try{
            manager.deleteEntry(40);
            fail();
        }catch (EntryDoesNotExistException e ){

        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void inFutureTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = "99.99";
        String details = "Food";
        String date = "3000-04-23";
        boolean purchase = true;

        try{

            manager.createEntry(amount,details,date,purchase);
            fail();
        }catch (FutureDateException e){

        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void createPurchaseTest(){
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase);

        Entry entry = fetcher.fetchAllEntrys().getChrono().get(0);

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
    }

    @Test
    public void createPurchaseWithCatTest(){
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UICategoryCreator categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();

        String goal = "200";
        String name = "food";
        int catID = categoryCreator.createBudgetCategory(goal,name);

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase,catID);

        Entry entry = fetcher.fetchAllEntrys().getChrono().get(0);

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
        assertEquals(catID,entry.getCatID());
    }

    @Test
    public void createPurchaseWithCatInvalidCatIDTest(){
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UICategoryCreator categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();

        String goal = "200";
        String name = "food";
        int catID = categoryCreator.createBudgetCategory(goal,name);

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        try{
            manager.createEntry(amount,details,date,purchase,Integer.MIN_VALUE);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e ){
            fail();
        }
    }
    @Test
    public void flagUnflaggedEntryTest(){
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

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
        assertTrue(entry.flagged());
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

    @Test
    public void unflagUnflaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase);

        Purchase entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);
        manager.flagPurchase(entry,false);
        entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
        assertFalse(entry.flagged());


    }

    @Test
    public void unflagFlaggedEntryTest(){
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
        manager.flagPurchase(entry,false);
        entry = (Purchase) fetcher.fetchAllEntrys().getChrono().get(0);

        assertTrue(amount.equals(entry.getAmount().getDisplay()));
        assertTrue(details.equals(entry.getDetails().getValue()));
        assertTrue("April 23, 1999".equals(entry.getDate().getDisplay()));
        assertFalse(entry.flagged());


    }

    @Test
    public void unflagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase);

        try{

            manager.flagPurchase(54,false);
            fail();
        }catch(PurchaseDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void flagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher fetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        String amount = "99.99";
        String details = "Food";
        String date = "1999-04-23";
        boolean purchase = true;

        manager.createEntry(amount,details,date,purchase);

        try{

            manager.flagPurchase(54,true);
            fail();
        }catch(PurchaseDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }
}
