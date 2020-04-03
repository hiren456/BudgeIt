package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
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
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriodFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchaseFactory;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class UIRucurringEntryManagerTest {
    //DSOs
    Purchase purchase;
    Income income;
    RecurrencePeriod period;
    RecurringIncome recurringIncome;
    RecurringPurchase recurringPurchase;

    //Mocked objects
    IDManager idManager;
    DateSource dateSource;
    Database db;

    //SUT
    UIRecurringEntryManager manager;

    @Before
    public void setUpMock(){
        this.idManager = mock(IDManager.class);
        this.dateSource = mock(DateSource.class);
        this.db = mock(Database.class);
        DatabaseHolder.initTestable(this.db);

        this.manager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager(this.idManager,this.dateSource);

        Amount goal;
        Details name;
        Date date;
        int entryID;
        int catID;
        int recurringEntryID;

        when(this.idManager.getDefaultID("Category")).thenReturn(0);

        this.period = RecurrencePeriodFactory.createRecurrencePeriod(7,0,0,0);

        goal = AmountFactory.fromString( "200.00");
        name = DetailsFactory.fromString( "Food");
        date = DateFactory.fromInts(2019,04,1);
        entryID = 24;
        this.purchase = PurchaseFactory.createPurchase(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

        recurringEntryID = 6;
        this.recurringPurchase = RecurringPurchaseFactory.createPurchase(goal,recurringEntryID,name,date,this.idManager.getDefaultID("Category"),this.period,false);

        goal = AmountFactory.fromString( "7000.00");
        name = DetailsFactory.fromString( "Paycheck");
        date = DateFactory.fromInts(2019,04,1);
        entryID = 25;
        this.income = IncomeFactory.createIncome(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

        recurringEntryID = 7;
        this.recurringIncome = RecurringIncomeFactory.createRecurringIncome(goal,recurringEntryID,name,date,this.idManager.getDefaultID("Category"),this.period);
    }

    @Test
    public void createRecurringPurchaseTest(){
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);
        int id = this.manager.createRecurringEntry(this.purchase.getEntryID(),this.period);

        when(this.idManager.getNewID("Recurring Entry")).thenReturn(this.recurringPurchase.getRecurringEntryID());

        verify(this.idManager).getNewID("Recurring Entry");

        ArgumentCaptor<RecurringPurchase> argument = ArgumentCaptor.forClass(RecurringPurchase.class);
        verify(this.db).insertRecurringEntry(argument.capture());
        assertTrue(this.recurringPurchase.equals(argument.getValue()));
    }

    @Test
    public void createRecurringIncomeTest(){
        when(this.db.selectDefaultEntryByID(this.income.getEntryID())).thenReturn(this.income);
        int id = this.manager.createRecurringEntry(this.income.getEntryID(),this.period);

        when(this.idManager.getNewID("Recurring Entry")).thenReturn(this.recurringIncome.getRecurringEntryID());

        verify(this.idManager).getNewID("Recurring Entry");

        ArgumentCaptor<RecurringIncome> argument = ArgumentCaptor.forClass(RecurringIncome.class);
        verify(this.db).insertRecurringEntry(argument.capture());
        assertTrue(this.recurringIncome.equals(argument.getValue()));
    }

    @Test
    public void createRecurringEntryWithInvalidEntryTest(){
        int invalidId = Integer.MAX_VALUE;
        when(this.db.selectDefaultEntryByID(invalidId)).thenReturn(null);

        try{
            int id = this.manager.createRecurringEntry(this.income.getEntryID(),this.period);
            fail();
        }catch (EntryDoesNotExistException e){

        }catch (Exception e ){
            fail();
        }
        verify(this.db).selectDefaultEntryByID(invalidId);
    }

    @Test
    public void noTimePassedReccuingIncomeTest(){
        // "put" income and recurring income into the db
        when(this.db.selectDefaultEntryByID(this.income.getEntryID())).thenReturn(this.income);
        when(this.db.selectRecurringEntryByID(this.recurringIncome.getRecurringEntryID()))
                .thenReturn(this.recurringIncome);
        ArrayList<RecurringEntry> allRecurr = new ArrayList<RecurringEntry>(1);
        allRecurr.add(this.recurringIncome);
        when(this.db.getAllRecurringEntries()).thenReturn(allRecurr);

        // Today is 2019-04-01
        Date now = DateFactory.fromString("2019-04-01");
        when(this.dateSource.now()).thenReturn(now);

        when(this.db.getDateLastChecked("Recurring Entry"))
                .thenReturn(DateFactory.fromString("2019-04-01"));

        this.manager.checkAllRecurringEntrys();

        // The manager shouldn't insert anything
        verify(this.db,never()).insertDefaultEntry(Mockito.any(Entry.class));
        verify(this.db,never()).insertCategory(Mockito.any(Category.class));
        verify(this.db,never()).insertRecurringEntry(Mockito.any(RecurringEntry.class));
    }

    @Test
    public void noPeriodPassedRecurringIncomeTest(){
        // "put" income and recurring income into the db
        when(this.db.selectDefaultEntryByID(this.income.getEntryID())).thenReturn(this.income);
        when(this.db.selectRecurringEntryByID(this.recurringIncome.getRecurringEntryID()))
                .thenReturn(this.recurringIncome);
        ArrayList<RecurringEntry> allRecurr = new ArrayList<RecurringEntry>(1);
        allRecurr.add(this.recurringIncome);
        when(this.db.getAllRecurringEntries()).thenReturn(allRecurr);

        // Today is 2019-04-01
        Date now = DateFactory.fromString("2019-04-02");
        when(this.dateSource.now()).thenReturn(now);

        when(this.db.getDateLastChecked("Recurring Entry"))
                .thenReturn(DateFactory.fromString("2019-04-01"));


        this.manager.checkAllRecurringEntrys();
        // The manager shouldn't insert anything
        verify(this.db,never()).insertDefaultEntry(Mockito.any(Entry.class));
        verify(this.db,never()).insertCategory(Mockito.any(Category.class));
        verify(this.db,never()).insertRecurringEntry(Mockito.any(RecurringEntry.class));

        // The manager should check all recurring entries
        verify(this.db).getAllRecurringEntries();

        // the manager should check the date last checked
        verify(this.db).getDateLastChecked("Recurring Entry");

        // the manager should update the dateLastChecked to today's date
        ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Date> dateArgument = ArgumentCaptor.forClass(Date.class);
        verify(this.db).updateDateLastChecked(stringArgument.capture(), dateArgument.capture());
        assertTrue(now.equals(dateArgument.getValue()));
        assertTrue("Recurring Entry".equals(stringArgument.getValue()));
    }

    @Test
    public void onePeriodPassedRecurringIncomeTest(){
        // "put" income and recurring income into the db
        when(this.db.selectDefaultEntryByID(this.income.getEntryID())).thenReturn(this.income);
        when(this.db.selectRecurringEntryByID(this.recurringIncome.getRecurringEntryID()))
                .thenReturn(this.recurringIncome);
        ArrayList<RecurringEntry> allRecurr = new ArrayList<RecurringEntry>(1);
        allRecurr.add(this.recurringIncome);
        when(this.db.getAllRecurringEntries()).thenReturn(allRecurr);

        // Today is 2019-04-08
        Date now = DateFactory.fromString("2019-04-08");
        when(this.dateSource.now()).thenReturn(now);

        when(this.db.getDateLastChecked("Recurring Entry"))
                .thenReturn(DateFactory.fromString("2019-04-01"));

        this.manager.checkAllRecurringEntrys();

        ArgumentCaptor<Entry> argument = ArgumentCaptor.forClass(Entry.class);
        verify(this.db).insertDefaultEntry(argument.capture());
        Entry onePeriodShiftedIncome = this.income
                .modifyEntry(this.income.getAmount(),
                        this.income.getDetails(),
                        DateFactory.fromString("2019-04-08"));
        assertTrue(onePeriodShiftedIncome.equals(argument.getValue()));

        verify(this.db).getAllRecurringEntries();
        verify(this.db).getDateLastChecked("Recurring Entry");

        // the manager should update the dateLastChecked to today's date
        ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Date> dateArgument = ArgumentCaptor.forClass(Date.class);
        verify(this.db).updateDateLastChecked(stringArgument.capture(), dateArgument.capture());
        assertTrue(now.equals(dateArgument.getValue()));
        assertTrue("Recurring Entry".equals(stringArgument.getValue()));
    }
}
