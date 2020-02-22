package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PurchaseTest {
    @Test
    public void ValidEntryWithFlagGetAmountTest() {

        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        assertTrue(entry.getAmount().equals(amount));
    }

    @Test
    public void ValidEntryWithFlagGetEntryIDTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        assertEquals(entry.getEntryID() , 42);
    }

    @Test
    public void ValidEntryWithFlagGetDetailsTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        assertTrue(entry.getDetails().equals(details));
    }

    @Test
    public void ValidEntryWithFlagGetDateTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        assertTrue(entry.getDate().equals(date));
    }
    @Test
    public void ValidEntryWithTrueFlagGetFlagTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Purchase entry = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        assertTrue(entry.flagged());
    }

    @Test
    public void ValidEntryWithFalseFlagGetFlagTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Purchase entry = PurchaseFactory.createPurchase(amount, entryID, details, date,false);
        assertFalse(entry.flagged());
    }

    public void ValidEntryGetFlagTest() {
        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Purchase entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
        assertFalse(entry.flagged());
    }
    @Test
    public void ValidEntryGetAmountTest() {

        //Create valid Entry
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        //test getAmount
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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
        Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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

            Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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

            Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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

            Entry entry = PurchaseFactory.createPurchase(amount, entryID, details, date);
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

        Entry entry1 = PurchaseFactory.createPurchase(amount, entryID, details, date);

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
    public void flaggedEntryequalsUnflaggedEntryTest(){
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry entry1 = PurchaseFactory.createPurchase(amount, entryID, details, date,true);
        Entry entry2 = PurchaseFactory.createPurchase(amount, entryID, details, date,false);

        assertFalse(entry1.equals(entry2));
        assertFalse(entry2.equals(entry1));
    }

    @Test
    public void incomeEqualsPurchaseTest(){
        Amount amount = AmountFactory.fromInt(999);
        int entryID = 42;
        Details details = DetailsFactory.fromString( "A very creative description");
        Date date = DateFactory.fromInts(1999,04,23);

        Entry income = IncomeFactory.createIncome(amount, entryID, details, date);
        Entry purchase = PurchaseFactory.createPurchase(amount, entryID, details, date);

        assertFalse(purchase.equals(income));
    }


}
