package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UIEntryManagerTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));

        Database db = DatabaseHolder.getDatabase();

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

    @Test
    public void deleteValidEntry(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date catDate = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, catDate, catID1);

        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date,catID1);

        Database db = DatabaseHolder.getDatabase();
        db.insertCategory(category);
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
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date catDate = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, catDate, catID1);

        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date,catID1);

        Database db = DatabaseHolder.getDatabase();
        db.insertCategory(category);
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

    // This test is new and should be replaced with mockito
    @Test
    public void changeGoalEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Entry oldEntry =  db.selectByID(24);
        Amount newAmount = AmountFactory.fromInt(50000);
        mod.changeAmount(24,newAmount);
        Entry newEntry =  db.selectByID(24);

        assertTrue(oldEntry.getDate().equals(newEntry.getDate()));
        assertTrue(oldEntry.getDetails().equals(newEntry.getDetails()));
        assertTrue(newAmount.equals(newEntry.getAmount()));
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeGoalNonExistentEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Amount newAmount = AmountFactory.fromInt(50000);

        try{
            mod.changeAmount(Integer.MIN_VALUE,newAmount);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }

    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeNameEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Entry oldEntry =  db.selectByID(24);
        Details newName = DetailsFactory.fromString("Better Food");
        mod.changeName(24,newName);
        Entry newEntry =  db.selectByID(24);

        assertTrue(oldEntry.getDate().equals(newEntry.getDate()));
        assertTrue(newName.equals(newEntry.getDetails()));
        assertTrue(oldEntry.getAmount().equals(newEntry.getAmount()));
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeNameNonExistentEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Details newName = DetailsFactory.fromString("Better Food");

        try{
            mod.changeName(Integer.MIN_VALUE,newName);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeDateEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Entry oldEntry =  db.selectByID(24);
        Date newDate = DateFactory.fromString("2018-03-21");
        mod.changeDate(24,newDate);
        Entry newEntry =  db.selectByID(24);

        assertTrue(newDate.equals(newEntry.getDate()));
        assertTrue(oldEntry.getDetails().equals(newEntry.getDetails()));
        assertTrue(oldEntry.getAmount().equals(newEntry.getAmount()));
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeDateNonExistentEntryTest() {
        UIEntryManager mod = UIEntryManagerFactory.createUIEntryManager();
        Database db = DatabaseHolder.getDatabase();

        Date newDate = DateFactory.fromString("2018-03-21");

        try{
            mod.changeDate(Integer.MIN_VALUE,newDate);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }
}
