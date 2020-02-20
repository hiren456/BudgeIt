package com.codemonkeys9.budgeit.dso.date;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.EntryFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void displayDateTest() {

        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        int month = 10;
        int year = 1999;
        java.util.Date date = new Date(year - 1900,month - 1,23);

        String displayDate = "23 Oct 1999";


        //test getAmount
        Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        assertEquals(displayDate.length(),entry.getDisplayDate().length());
        assertTrue("Actual:"+entry.getDisplayDate() + " Expected:"+ displayDate,entry.getDisplayDate().equals(displayDate));
    }
    @Test
    public void validDateTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
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

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
            fail("Passing a null date does not cause an exception");
        }catch (Exception e){

        }
    }
}
