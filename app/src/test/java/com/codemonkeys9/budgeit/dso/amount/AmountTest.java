package com.codemonkeys9.budgeit.dso.amount;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.EntryFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AmountTest {
    @Test
    public void displayAmountTest() {

        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        int month = 10;
        int year = 1999;
        Date date = new Date(year - 1900,month - 1,23);

        String displayAmount = "9.99";


        //test getAmount
        Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        assertTrue("Actual:"+entry.getDisplayAmount() + " Expected:"+ displayAmount,entry.getDisplayAmount().equals(displayAmount));
    }
    @Test
    public void amountZeroTest() {
        int amount = 0;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with amount 0 causes an exception");
        }
    }

    @Test
    public void amountPositiveTest() {
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with positive causes an exception");
        }
    }

    @Test
    public void amountNegativeTest() {
        int amount = -100;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with negative amount causes an exception");
        }
    }
}
