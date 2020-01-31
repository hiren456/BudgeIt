package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.Entry.EntryFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class EntryTest {
    @Test
    public void ValidEntryGetAmountTest() {

        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        //test getAmount
        Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        assertEquals(entry.getAmount(), 999);
    }

    @Test
    public void ValidEntryGetEntryIDTest() {
        //Create valid com.codemonkeys9.budgeit.Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);


        Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        assertEquals(entry.getEntryID(),42);
    }

    @Test
    public void ValidEntryGetDetailsTest() {
        //Create valid com.codemonkeys9.budgeit.Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);
        String details2 = "A very creative description";

        Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        assertTrue(details2.equals(entry.getDetails()));
    }

    @Test
    public void ValidEntryGetDateTest() {
        //Create valid com.codemonkeys9.budgeit.Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);
        Date date2 = new Date(1999,04,23);


        Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        assertTrue(date2.equals(entry.getDate()));
    }

    @Test
    public void amountZeroTest() {
        int amount = 0;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
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

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
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

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with negative amount causes an exception");
        }
    }

    @Test
    public void entryIDZeroTest() {
        int amount = 999;
        int entryID = 0;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with entry 0 causes an exception");
        }
    }

    @Test
    public void entryIDNegativeTest() {
        int amount = 999;
        int entryID = -100;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with negative entryID causes an exception");
        }
    }

    @Test
    public void entryIDPositiveTest() {
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Making an entry with positive entryID causes an exception");
        }
    }

    @Test
    public void validDetailsNullTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Passing \"A creative description\" for details String causes an exception");
        }
    }

    @Test
    public void detailsNullTest() {
        int amount = 999;
        int entryID = 52;
        String details = null;
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
            fail("Passing null for details String does not cause an exception");
        }catch (Exception e){

        }
    }

    @Test
    public void detailsEmptyTest() {
        int amount = 999;
        int entryID = 52;
        String details = "";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Passing empty string causes an exception");
        }
    }

    @Test
    public void validDateTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Passing the date 23/04/1999 causes an exception");
        }
    }

    @Test
    public void nullDateTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = null;

        try{

            Entry entry = new EntryFactory().createEntry(amount, entryID, details, date);
            fail("Passing a null date does not cause an exception");
        }catch (Exception e){

        }
    }

    //modifyEntry Tests

    @Test
    public void modifyEntryTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = new Date(1999,04,23);

        Entry entry1 = new EntryFactory().createEntry(amount, entryID, details, date);

        int newAmount = 123;
        String newDetails = "Another creative description";
        Date newDate = new Date(2000,05,24);

        Entry entry2 = entry1.modifyEntry(newAmount,newDetails,newDate);


        assertEquals("When an entrys modifyEntry method has been called," +
                "the original amount gets changed",entry1.getAmount(), 999);
        assertEquals("When an entrys modifyEntry method has been called," +
                "the original ID gets changed",entry1.getEntryID(), 52);

        assertEquals("When an entrys modifyEntry method get called," +
                "the returned entrys amount is not what it should be",entry2.getAmount(), 123);
        assertEquals("When an entrys modifyEntry method get called," +
                "the returned entrys id is not what it should be",entry2.getEntryID(), 52);


        assertTrue("When an entrys modifyEntry method has been called," +
                "the original details string gets changed","A creative description".equals(entry1.getDetails()));
        assertTrue("When an entrys modifyEntry method has been called," +
                "the original date gets changed",date.equals(entry1.getDate()));


        assertTrue("When an entrys modifyEntry method gets called," +
                "the returned entrys details string gets changed","Another creative description".equals(entry2.getDetails()));
        assertTrue("When an entrys modifyEntry method gets called," +
                "the returned entrys date gets changed",newDate.equals(entry2.getDate()));
    }

}
