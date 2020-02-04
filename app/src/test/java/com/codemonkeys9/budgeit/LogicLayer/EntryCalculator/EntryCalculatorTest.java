package com.codemonkeys9.budgeit.LogicLayer.EntryCalculator;

import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.Entry.EntryFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntryCalculatorTest {
    @Test
    public void sumManyTest() {
        //Create Database

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

        // add them to a list
        List<Entry> entryList = new ArrayList<Entry>(4);
        entryList.add(0,entry1);
        entryList.add(1,entry2);
        entryList.add(2,entry3);
        entryList.add(3,entry4);

        int expectedSum = 0;
        expectedSum = expectedSum + 7249;
        expectedSum = expectedSum + 520;
        expectedSum = expectedSum + 604;
        expectedSum = expectedSum - 724;

        int actualSum = new EntryCalculatorFactory().createEntryCalculator().sumEntryList(entryList);

        assertEquals(expectedSum,actualSum);
    }

    @Test
    public void sumOneTest() {
        //Create valid Entry1
        int amount1 = 7249;
        int entryID1 = 81;
        String details1 = "Some letters put next to eachother";
        Date date1 = new Date(2001,07,07);
        Entry entry1 = new EntryFactory().createEntry(amount1,entryID1,details1,date1);

        // add them to a list
        List<Entry> entryList = new ArrayList<Entry>(4);
        entryList.add(0,entry1);

        int expectedSum = 0;
        expectedSum = expectedSum + 7249;

        int actualSum = new EntryCalculatorFactory().createEntryCalculator().sumEntryList(entryList);

        assertEquals(expectedSum,actualSum);
    }

    @Test
    public void sumNoneTest() {
        // add them to a list
        List<Entry> entryList = new ArrayList<Entry>(4);

        int expectedSum = 0;

        int actualSum = new EntryCalculatorFactory().createEntryCalculator().sumEntryList(entryList);

        assertEquals(expectedSum,actualSum);
    }
}
