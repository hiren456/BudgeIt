package com.codemonkeys9.budgeit.Database;


import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.Entry.EntryFactory;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void idCounterInitialValueTest() {
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);

        assertEquals("When a database is initialized, " +
                "the intitial value for the id counter, passed as a parameter," +
                "is not what is returned by getIDCounter.",database.getIDCounter(), 42);
    }

    @Test
    public void idCounterUpdateValueTest() {
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);
        database.updateIDCounter(23);

        assertEquals("When updateIDCounter is called, " +
                "the id counter returned by getIDCounter" +
                "is not the same as what was passed to updateIDCounter.",database.getIDCounter(), 23);
    }

    @Test
    public void insertThenSelectTest() {
        //Create Database
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);

        //Create Entry

        //Create valid Entry
        int amount1 = 7249;
        int entryID1 = 81;
        String details1 = "Some letters put next to eachother";
        Date date1 = new Date(2002,07,07);
        Entry entry1 = new EntryFactory().createEntry(amount1,entryID1,details1,date1);

        //insert it into the database
        database.insertEntry(entry1);

        Entry retEntry1 = database.selectByID(81);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return an entry using selecBYID",retEntry1);
        assertEquals("Database returns a entry with the wrong amount using selectByID"
                ,7249,retEntry1.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByID"
                ,81,retEntry1.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID"
                ,"Some letters put next to eachother".equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID"
                ,date1.equals(retEntry1.getDate()));
    }
}
