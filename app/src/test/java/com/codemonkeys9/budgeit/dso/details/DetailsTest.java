package com.codemonkeys9.budgeit.dso.details;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.EntryFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DetailsTest {
    @Test
    public void validDetailsNullTest() {
        int amount = 999;
        int entryID = 52;
        String details = "A creative description";
        Date date = new Date(1999,04,23);

        try{

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
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

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
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

            Entry entry = EntryFactory.createEntry(amount, entryID, details, date);
        }catch (Exception e){

            fail("Passing empty string causes an exception");
        }
    }
}
