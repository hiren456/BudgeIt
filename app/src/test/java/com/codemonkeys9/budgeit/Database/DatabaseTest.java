package com.codemonkeys9.budgeit.Database;


import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.Entry.EntryFactory;

import org.junit.Test;

import java.util.Date;
import java.util.List;

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

    @Test
    public void insertManyThenSelectByIDTest() {
        //Create Database
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);

        //Create valid Entry1
        int amount1 = 7249;
        int entryID1 = 81;
        String details1 = "Some letters put next to eachother";
        Date date1 = new Date(2001,07,07);
        Entry entry1 = new EntryFactory().createEntry(amount1,entryID1,details1,date1);

        //Create valid Entry2
        int amount2 = 520;
        int entryID2 = 72;
        String details2 = "Some letters put next to eachother again";
        Date date2 = new Date(2001,11,07);
        Entry entry2 = new EntryFactory().createEntry(amount2,entryID2,details2,date2);

        //Create valid Entry3
        int amount3 = 604;
        int entryID3 = -7;
        String details3 = "I am running out of ideas";
        Date date3 = new Date(2009,07,06);
        Entry entry3 = new EntryFactory().createEntry(amount3,entryID3,details3,date3);

        //Create valid Entry4
        int amount4 = -724;
        int entryID4 = 6;
        String details4 = "Ender's game is an interesting book";
        Date date4 = new Date(2009,07,07);
        Entry entry4 = new EntryFactory().createEntry(amount4,entryID4,details4,date4);

        //insert them into the database
        database.insertEntry(entry1);
        database.insertEntry(entry2);
        database.insertEntry(entry3);
        database.insertEntry(entry4);


        Entry retEntry2 = database.selectByID(72);
        Entry retEntry3 = database.selectByID(-7);
        Entry retEntry4 = database.selectByID(6);

        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 2",retEntry2);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 3",retEntry3);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 4",retEntry4);

        // test that retEntry2 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByID with many inserts"
                ,520,retEntry2.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                ,72,retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                ,"Some letters put next to eachother again".equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                ,date2.equals(retEntry2.getDate()));


        // test that retEntry3 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByID with many inserts"
                ,604,retEntry3.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                ,-7,retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , "I am running out of ideas".equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                ,date3.equals(retEntry3.getDate()));


        // test that retEntry4 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByID with many inserts"
                ,-724,retEntry4.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                ,6,retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                ,"Ender's game is an interesting book".equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                ,date4.equals(retEntry4.getDate()));
    }
    @Test
    public void insertManyThenSelectByDateTest() {
        //Create Database
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);

        //Create valid Entry1
        int amount1 = 7249;
        int entryID1 = 81;
        String details1 = "Some letters put next to eachother";
        Date date1 = new Date(2001,07,07);
        Entry entry1 = new EntryFactory().createEntry(amount1,entryID1,details1,date1);

        //Create valid Entry2
        int amount2 = 520;
        int entryID2 = 72;
        String details2 = "Some letters put next to eachother again";
        Date date2 = new Date(2001,11,07);
        Entry entry2 = new EntryFactory().createEntry(amount2,entryID2,details2,date2);

        //Create valid Entry3
        int amount3 = 604;
        int entryID3 = -7;
        String details3 = "I am running out of ideas";
        Date date3 = new Date(2009,07,06);
        Entry entry3 = new EntryFactory().createEntry(amount3,entryID3,details3,date3);

        //Create valid Entry4
        int amount4 = -724;
        int entryID4 = 6;
        String details4 = "Ender's game is an interesting book";
        Date date4 = new Date(2009,07,07);
        Entry entry4 = new EntryFactory().createEntry(amount4,entryID4,details4,date4);

        //insert them into the database
        database.insertEntry(entry1);
        database.insertEntry(entry2);
        database.insertEntry(entry3);
        database.insertEntry(entry4);

        List<Entry> retList = database.selectByDate(new Date(2001,10,07),new Date(2009,07,07));

        // test that we got what was expected
        assertEquals("Expected select by date to return 3 entrys but it does not",retList.size(),3);

        Entry retEntry2 = retList.get(0);
        Entry retEntry3 = retList.get(1);
        Entry retEntry4 = retList.get(2);

        // test that retEntry2 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByDate"
                ,520,retEntry2.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByDate"
                ,72,retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByDate"
                ,"Some letters put next to eachother again".equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByDate"
                ,date2.equals(retEntry2.getDate()));


        // test that retEntry3 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByDate"
                ,604,retEntry3.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByDate"
                ,-7,retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByDate"
                , "I am running out of ideas".equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByDate"
                ,date3.equals(retEntry3.getDate()));


        // test that retEntry4 is the one we want
        assertEquals("Database returns a entry with the wrong amount using selectByDate"
                ,-724,retEntry4.getAmount());
        assertEquals("Database returns a entry with the wrong entryID using selectByDate"
                ,6,retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByDate"
                ,"Ender's game is an interesting book".equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByDate"
                ,date4.equals(retEntry4.getDate()));
    }
}
