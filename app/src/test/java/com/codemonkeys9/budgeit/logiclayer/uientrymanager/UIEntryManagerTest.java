package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.database.Database;
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
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class UIEntryManagerTest {
    // DSOs
    Purchase purchase;
    Purchase categorizedPurchase;
    Income income;
    Income categorizedIncome;
    Category category;

    // mocked object
    Database db;
    IDManager idManager;

    // SUT
    UIEntryManager manager;

    @Before
    public void createDb() {
        this.db = mock(Database.class);
        DatabaseHolder.initTestable(this.db);
        this.idManager = mock(IDManager.class);
        this.manager = UIEntryManagerFactory.createUIEntryManager(this.idManager);


        Amount goal;
        Details name;
        Date date;
        int entryID;
        int catID;

        when(this.idManager.getDefaultID("Category")).thenReturn(0);

        goal = AmountFactory.fromString( "200.00");
        name = DetailsFactory.fromString( "Food");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 24;
        this.purchase = PurchaseFactory.createPurchase(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

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
        this.income = IncomeFactory.createIncome(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

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
        when(this.db.deleteDefaultEntry(24)).thenReturn(true);
        manager.deleteEntry(24);
        verify(this.db).deleteDefaultEntry(24);
    }

    @Test
    public void deleteInValidEntry(){
        when(this.db.deleteDefaultEntry(40)).thenReturn(false);

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
        String amount = this.purchase.getAmount().getDisplay();
        String details = this.purchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        boolean purchase = true;

        when(this.idManager.getNewID("Entry")).thenReturn(24);
        int entryId = manager.createEntry(amount,details,date,purchase);

        assertEquals(24,entryId);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).insertDefaultEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));

        verify(this.idManager).getNewID("Entry");
    }

    @Test
    public void createPurchaseWithCatTest(){
        String amount = this.categorizedPurchase.getAmount().getDisplay();
        String details = this.categorizedPurchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        int catID = this.categorizedPurchase.getCatID();
        boolean purchase = true;

        when(this.idManager.getNewID("Entry")).thenReturn(24);
        when(this.db.selectCategoryByID(this.category.getID())).thenReturn(this.category);
        when(this.db.selectDefaultEntryByID(this.categorizedPurchase.getEntryID())).thenReturn(this.categorizedPurchase);

        int entryID = manager.createEntry(amount,details,date,purchase,catID);

        assertEquals(entryID, 24);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).insertDefaultEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));

        verify(this.idManager).getNewID("Entry");
    }

    @Test
    public void createPurchaseWithCatInvalidCatIDTest(){
        String amount = this.categorizedPurchase.getAmount().getDisplay();
        String details = this.categorizedPurchase.getDetails().getValue();
        // Date object needs a getValue method
        String date = "1999-04-23";
        int invalidCatID = Integer.MIN_VALUE;
        boolean purchase = true;

        when(this.idManager.getNewID("Entry")).thenReturn(24);
        when(this.db.selectCategoryByID(invalidCatID)).thenReturn(null);
        when(this.db.selectDefaultEntryByID(this.categorizedPurchase.getEntryID())).thenReturn(this.purchase);

        try{
            manager.createEntry(amount,details,date,purchase,Integer.MIN_VALUE);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e ){
            fail();
        }

        verify(this.idManager).getNewID("Entry");
    }
    @Test
    public void flagUnflaggedEntryTest(){
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        manager.flagPurchase(this.purchase,true);

        Purchase flaggedPurchase = this.purchase.flag();

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(flaggedPurchase.equals(argument.getValue()));
    }

    @Test
    public void flagFlaggedEntryTest(){
        Purchase flaggedPurchase = this.purchase.flag();
        when(this.db.selectDefaultEntryByID(flaggedPurchase.getEntryID())).thenReturn(flaggedPurchase);

        manager.flagPurchase(flaggedPurchase,true);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(flaggedPurchase.equals(argument.getValue()));
    }

    @Test
    public void unflagUnflaggedEntryTest(){
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        manager.flagPurchase(this.purchase,false);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));
    }

    @Test
    public void unflagFlaggedEntryTest(){
        Purchase flaggedPurchase = this.purchase.flag();
        when(this.db.selectDefaultEntryByID(flaggedPurchase.getEntryID())).thenReturn(flaggedPurchase);

        manager.flagPurchase(flaggedPurchase,false);

        ArgumentCaptor<Purchase> argument = ArgumentCaptor.forClass(Purchase.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(this.purchase.equals(argument.getValue()));
    }

    @Test
    public void unflagNonexistentEntryTest(){
        int invalidEntryID = Integer.MAX_VALUE;

        when(this.db.selectDefaultEntryByID(invalidEntryID)).thenReturn(null);

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
        int invalidEntryID = Integer.MAX_VALUE;

        when(this.db.selectDefaultEntryByID(invalidEntryID)).thenReturn(null);

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
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Amount newAmount = AmountFactory.fromInt(50000);
        manager.changeAmount(this.purchase.getEntryID(),newAmount);
        Entry modifiedPurchase = this.purchase.modifyEntry(newAmount,this.purchase.getDetails(),this.purchase.getDate());

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    @Test
    public void changeGoalNonExistentEntryTest() {
        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectDefaultEntryByID(invalidEntryId)).thenReturn(null);

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
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Details newName = DetailsFactory.fromString("Better Food");
        manager.changeName(this.purchase.getEntryID(),newName);
        Entry modifiedPurchase = this.purchase.modifyEntry(this.purchase.getAmount(),newName,this.purchase.getDate());

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    @Test
    public void changeNameNonExistentEntryTest() {
        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectDefaultEntryByID(invalidEntryId)).thenReturn(null);

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
        when(db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);

        Date newDate = DateFactory.fromString("2018-03-21");
        manager.changeDate(this.purchase.getEntryID(),newDate);
        Entry modifiedPurchase = this.purchase.modifyEntry(this.purchase.getAmount(),this.purchase.getDetails(),newDate);

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).updateDefaultEntry(argument.capture());
        assertTrue(modifiedPurchase.equals(argument.getValue()));
    }

    @Test
    public void changeDateNonExistentEntryTest() {
        int invalidEntryId = Integer.MAX_VALUE;
        when(this.db.selectDefaultEntryByID(invalidEntryId)).thenReturn(null);

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
