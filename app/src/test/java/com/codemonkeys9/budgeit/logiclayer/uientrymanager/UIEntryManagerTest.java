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
import com.codemonkeys9.budgeit.dso.entry.Income;
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
import org.mockito.ArgumentCaptor;

import java.util.IdentityHashMap;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UIEntryManagerTest {
    Purchase purchase;
    Purchase categorizedPurchase;

    Income income;
    Income categorizedIncome;

    Category category;

    // mocked object
    Database db;
    IDManager idManager;

    @Before
    public void createDb() {
        this.idManager = IDManagerFactory.createIDManager();
        this.db = mock(Database.class);

        DatabaseHolder.initTestable(this.db);

        Amount goal;
        Details name;
        Date date;
        int entryID;
        int catID;

        goal = AmountFactory.fromString( "200.00");
        name = DetailsFactory.fromString( "Food");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 24;
        this.purchase = PurchaseFactory.createPurchase(goal,entryID,name,date,idManager.getDefaultID("Category"));

        goal = AmountFactory.fromString( "200.00");
        name = DetailsFactory.fromString( "Food");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 24;
        catID = 7;
        this.categorizedPurchase = PurchaseFactory.createPurchase(goal,entryID,name,date,catID);

        goal = AmountFactory.fromString( "7000.00");
        name = DetailsFactory.fromString( "Paycheck");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 25;
        this.income = IncomeFactory.createIncome(goal,entryID,name,date,idManager.getDefaultID("Category"));

        goal = AmountFactory.fromString( "7000.00");
        name = DetailsFactory.fromString( "Paycheck");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 25;
        catID = 7;
        this.categorizedIncome = IncomeFactory.createIncome(goal,entryID,name,date,catID);

        goal = AmountFactory.fromString( "10000.00");
        name = DetailsFactory.fromString( "Budget");
        date = DateFactory.fromString("now");
        catID = 7;
        this.category = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
    }

    @Test
    public void deleteValidEntry(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.deleteEntry(42)).thenReturn(true);
        manager.deleteEntry(42);
        verify(this.db).deleteEntry(42);
    }

    @Test
    public void deleteInValidEntry(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.deleteEntry(40)).thenReturn(false);

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

        String amount = "200.00";
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

        // db should not be touched
        verifyZeroInteractions(this.db);
    }
    @Test
    public void createPurchaseTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = this.purchase.getAmount().getDisplay();
        String details = this.purchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        boolean purchase = true;

        // This assumes something about the id manager's implementation
        // This is bad and needs to be fixed by allowing a
        // dependency injection for the id manager
        when(this.db.getIDCounter("Entry")).thenReturn(24 - 1);

        int entryId = manager.createEntry(amount,details,date,purchase);

        assertEquals(24,entryId);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).insertEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));

        verify(this.db).getIDCounter("Entry");
    }

    @Test
    public void createPurchaseWithCatTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = this.categorizedPurchase.getAmount().getDisplay();
        String details = this.categorizedPurchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        int catID = this.categorizedPurchase.getCatID();
        boolean purchase = true;

        when(this.db.getIDCounter("Entry")).thenReturn(24-1);
        when(this.db.selectCategoryByID(this.category.getID())).thenReturn(this.category);
        when(this.db.selectByID(this.categorizedPurchase.getEntryID())).thenReturn(this.categorizedPurchase);

        int entryID = manager.createEntry(amount,details,date,purchase,catID);

        assertEquals(entryID, 24);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).insertEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));

        verify(this.db).getIDCounter("Entry");
    }

    @Test
    public void createPurchaseWithCatInvalidCatIDTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = this.categorizedPurchase.getAmount().getDisplay();
        String details = this.categorizedPurchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        int invalidCatID = Integer.MIN_VALUE;
        boolean purchase = true;

        when(this.db.getIDCounter("Entry")).thenReturn(24-1);
        when(this.db.selectCategoryByID(invalidCatID)).thenReturn(null);
        when(this.db.selectByID(this.categorizedPurchase.getEntryID())).thenReturn(this.purchase);

        try{
            manager.createEntry(amount,details,date,purchase,Integer.MIN_VALUE);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e ){
            fail();
        }

        verify(this.db).getIDCounter("Entry");
    }
    @Test
    public void flagUnflaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.selectByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        manager.flagPurchase(this.purchase,true);

        Purchase flaggedPurchase = this.purchase.flag();

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(flaggedPurchase.equals(argument.getValue()));
    }

    @Test
    public void flagFlaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        Purchase flaggedPurchase = this.purchase.flag();
        when(this.db.selectByID(flaggedPurchase.getEntryID())).thenReturn(flaggedPurchase);

        manager.flagPurchase(flaggedPurchase,true);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(flaggedPurchase.equals(argument.getValue()));
    }

    @Test
    public void unflagUnflaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.selectByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        manager.flagPurchase(this.purchase,false);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));
    }

    @Test
    public void unflagFlaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        Purchase flaggedPurchase = this.purchase.flag();
        when(this.db.selectByID(flaggedPurchase.getEntryID())).thenReturn(flaggedPurchase);

        manager.flagPurchase(flaggedPurchase,false);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));
    }

    @Test
    public void unflagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        int invalidEntryID = Integer.MAX_VALUE;

        when(this.db.selectByID(invalidEntryID)).thenReturn(null);

        try{
            manager.flagPurchase(invalidEntryID,false);
            fail();
        }catch(EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void flagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        int invalidEntryID = Integer.MAX_VALUE;

        when(this.db.selectByID(invalidEntryID)).thenReturn(null);

        try{
            manager.flagPurchase(invalidEntryID,true);
            fail();
        }catch(EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void changeGoalEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.selectByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Amount newAmount = AmountFactory.fromInt(50000);
        manager.changeAmount(this.purchase.getEntryID(),newAmount);
        Entry modifiedPurchase = this.purchase.modifyEntry(newAmount,this.purchase.getDetails(),this.purchase.getDate());

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeGoalNonExistentEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectByID(invalidEntryId)).thenReturn(null);

        Amount newAmount = AmountFactory.fromInt(50000);

        try{
            manager.changeAmount(Integer.MIN_VALUE,newAmount);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void changeNameEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.selectByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Details newName = DetailsFactory.fromString("Better Food");
        manager.changeName(this.purchase.getEntryID(),newName);
        Entry modifiedPurchase = this.purchase.modifyEntry(this.purchase.getAmount(),newName,this.purchase.getDate());

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    @Test
    public void changeNameNonExistentEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectByID(invalidEntryId)).thenReturn(null);

        Details newName = DetailsFactory.fromString("Better Food");

        try{
            manager.changeName(Integer.MIN_VALUE,newName);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void changeDateEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        when(this.db.selectByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Date newDate = DateFactory.fromString("2018-03-21");
        manager.changeDate(this.purchase.getEntryID(),newDate);
        Entry modifiedPurchase = this.purchase.modifyEntry(this.purchase.getAmount(),this.purchase.getDetails(),newDate);

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    // This test is new and should be replaced with mockito
    @Test
    public void changeDateNonExistentEntryTest() {
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectByID(invalidEntryId)).thenReturn(null);

        Date newDate = DateFactory.fromString("2018-03-21");

        try{
            manager.changeDate(Integer.MIN_VALUE,newDate);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }
    }
}
