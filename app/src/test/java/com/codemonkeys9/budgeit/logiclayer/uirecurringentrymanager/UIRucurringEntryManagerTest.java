package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import android.provider.Contacts;
import android.provider.ContactsContract;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriodFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchaseFactory;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
    Database db;

    //SUT
    UIRecurringEntryManager manager;

    @Before
    public void setUpMock(){
        this.idManager = mock(IDManager.class);
        this.db = mock(Database.class);
        DatabaseHolder.initTestable(this.db);

        this.manager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager(this.idManager);

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
        date = DateFactory.fromInts(1999,04,23);
        entryID = 24;
        this.purchase = PurchaseFactory.createPurchase(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

        recurringEntryID = 6;
        this.recurringPurchase = RecurringPurchaseFactory.createPurchase(goal,recurringEntryID,name,date,this.idManager.getDefaultID("Category"),this.period,false);

        goal = AmountFactory.fromString( "7000.00");
        name = DetailsFactory.fromString( "Paycheck");
        date = DateFactory.fromInts(1999,04,23);
        entryID = 25;
        this.income = IncomeFactory.createIncome(goal,entryID,name,date,this.idManager.getDefaultID("Category"));

        recurringEntryID = 7;
        this.recurringIncome = RecurringIncomeFactory.createRecurringIncome(goal,recurringEntryID,name,date,this.idManager.getDefaultID("Category"),this.period);

        //"put" the entry into the database
        when(this.db.selectDefaultEntryByID(this.purchase.getEntryID())).thenReturn(this.purchase);
        when(this.db.selectDefaultEntryByID(this.income.getEntryID())).thenReturn(this.income);
    }

    @Test
    public void createRecurringPurchaseTest(){
        int id = this.manager.createRecurringEntry(this.purchase.getEntryID(),this.period);

        when(this.idManager.getNewID("Recurring Entry")).thenReturn(this.recurringPurchase.getRecurringEntryID());

        verify(this.idManager).getNewID("Recurring Entry");

        ArgumentCaptor<RecurringPurchase> argument = ArgumentCaptor.forClass(RecurringPurchase.class);
        verify(this.db).insertRecurringEntry(argument.capture());
        assertTrue(this.recurringPurchase.equals(argument.getValue()));
    }

    @Test
    public void createRecurringIncomeTest(){
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
}
