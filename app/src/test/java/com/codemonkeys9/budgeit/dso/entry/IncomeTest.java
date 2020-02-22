package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class IncomeTest {
    @Test
    public void ValidEntryGetAmountTest() {

        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        assertTrue(entry.getAmount().equals(amount));
    }

    @Test
    public void ValidEntryGetEntryIDTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        assertEquals(entry.getEntryID() , 42);
    }

    @Test
    public void ValidEntryGetDetailsTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        assertTrue(entry.getDetails().equals(details));
    }

    @Test
    public void ValidEntryGetDateTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        assertTrue(entry.getDate().equals(date));
    }


    @Test
    public void entryIDZeroTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 0;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        try{

            Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with entryID 0 causes an exception");
        }
    }

    @Test
    public void entryIDNegativeTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = -37;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        try{

            Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with negative entryID causes an exception");
        }
    }

    @Test
    public void entryIDPositiveTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        try{

            Entry entry = IncomeFactory.createIncome(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with positive entryID causes an exception");
        }
    }

    @Test
    public void modifyEntryTest() {
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry entry1 = IncomeFactory.createIncome(amount, entryID, details, date);

        Amount newAmount = AmountFactory.fromInt(123);
        Details newDetails = DetailsFactory.fromString( "A very creative description");
        Date newDate = DateFactory.fromInts(2000,05,24);

        Entry entry2 = entry1.modifyEntry(newAmount,newDetails,newDate);


        assertTrue("When an entrys modifyEntry method has been called," +
                "the original amount gets changed",entry1.getAmount().equals(amount));
        assertEquals("When an entrys modifyEntry method has been called," +
                "the original ID gets changed",entry1.getEntryID(), 42);
        assertTrue("When an entrys modifyEntry method has been called," +
                "the original details string gets changed",entry1.getDetails().equals(details));
        assertTrue("When an entrys modifyEntry method has been called," +
                "the original date gets changed",entry1.getDate().equals(date));

        assertTrue("When an entrys modifyEntry method get called," +
                "the returned entrys amount is not what it should be",entry2.getAmount().equals(newAmount));
        assertEquals("When an entrys modifyEntry method get called," +
                "the returned entrys id is not what it should be",entry2.getEntryID(), 42);
        assertTrue("When an entrys modifyEntry method gets called," +
                "the returned entrys details is not what it should be",entry2.getDetails().equals(newDetails));
        assertTrue("When an entrys modifyEntry method gets called," +
                "the returned entrys date is not what it should be",entry2.getDate().equals(newDate));
    }

    @Test
    public void incomeEqualsPurchaseTest(){
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry income = IncomeFactory.createIncome(amount, entryID, details, date);
        Entry purchase = PurchaseFactory.createPurchase(amount, entryID, details, date);

        assertFalse(income.equals(purchase));
    }
}
