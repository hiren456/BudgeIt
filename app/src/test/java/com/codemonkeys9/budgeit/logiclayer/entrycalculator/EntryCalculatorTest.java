package com.codemonkeys9.budgeit.logiclayer.entrycalculator;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListFactory;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EntryCalculatorTest {
    @Before
    public void resetDatabase() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = DatabaseHolder.class.getDeclaredField("db");
        instance.setAccessible(true);
        instance.set(null, null);
        DatabaseHolder.init();
    }
    @Test
    public void sumManyTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001,11,7);
        Entry entry2 = IncomeFactory.createIncome(amount2,entryID2,details2,date2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009,7,6);
        Entry entry3 = IncomeFactory.createIncome(amount3,entryID3,details3,date3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009,7,7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4,entryID4,details4,date4,false);

        // add them to a list
        List<Entry> entries = new ArrayList<Entry>(4);
        entries.add(0,entry1);
        entries.add(1,entry2);
        entries.add(2,entry3);
        entries.add(3,entry4);
        EntryList entryList = EntryListFactory.fromChrono(entries);

        int expectedSum = 0;
        expectedSum += 7249;
        expectedSum += 520;
        expectedSum += 604;
        expectedSum -= 724;

        Amount actualSum = EntryCalculatorFactory.createEntryCalculator().sumEntryList(entryList);
        assertEquals(actualSum.getValue(),expectedSum);
    }

    @Test
    public void sumOneTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1);

        // add them to a list
        List<Entry> entries = new ArrayList<Entry>(4);
        entries.add(0,entry1);
        EntryList entryList = EntryListFactory.fromChrono(entries);

        int expectedSum = 0;
        expectedSum = expectedSum + 7249;

        Amount actualSum = EntryCalculatorFactory.createEntryCalculator().sumEntryList(entryList);
        assertEquals(actualSum.getValue(),expectedSum);
    }

    @Test
    public void sumNoneTest() {
        // add them to a list
        List<Entry> entries = new ArrayList<Entry>(4);
        EntryList entryList = EntryListFactory.fromChrono(entries);

        int expectedSum = 0;

        Amount actualSum = EntryCalculatorFactory.createEntryCalculator().sumEntryList(entryList);
        assertEquals(actualSum.getValue(),expectedSum);
    }
}
