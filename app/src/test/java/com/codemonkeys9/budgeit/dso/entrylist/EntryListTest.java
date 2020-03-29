package com.codemonkeys9.budgeit.dso.entrylist;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EntryListTest {
    @Test
    public void getChronoTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        int catID2 = 24;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001,11,7);
        Entry entry2 = IncomeFactory.createIncome(amount2,entryID2,details2,date2,catID2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        int catID3 = 25;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009,7,6);
        Entry entry3 = IncomeFactory.createIncome(amount3,entryID3,details3,date3,catID3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        int catID4 = 26;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009,7,7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4,entryID4,details4,date4,catID4);

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryList entryList = EntryListFactory.fromChrono(ll);

        List<Entry> chrono = entryList.getChrono();

        assertTrue(ll.get(0).equals(chrono.get(0)));
        assertTrue(ll.get(1).equals(chrono.get(1)));
        assertTrue(ll.get(2).equals(chrono.get(2)));
        assertTrue(ll.get(3).equals(chrono.get(3)));
    }

    @Test
    public void getReverseChronoTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        int catID2 = 24;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001,11,7);
        Entry entry2 = IncomeFactory.createIncome(amount2,entryID2,details2,date2,catID2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        int catID3 = 25;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009,7,6);
        Entry entry3 = IncomeFactory.createIncome(amount3,entryID3,details3,date3,catID3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        int catID4 = 26;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009,7,7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4,entryID4,details4,date4,catID4);

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryList entryList = EntryListFactory.fromChrono(ll);

        List<Entry> reverseChrono = entryList.getReverseChrono();

        assertTrue(ll.get(0).equals(reverseChrono.get(3)));
        assertTrue(ll.get(1).equals(reverseChrono.get(2)));
        assertTrue(ll.get(2).equals(reverseChrono.get(1)));
        assertTrue(ll.get(3).equals(reverseChrono.get(0)));
    }

    @Test
    public void sizeTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        int catID2 = 24;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001,11,7);
        Entry entry2 = IncomeFactory.createIncome(amount2,entryID2,details2,date2,catID2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        int catID3 = 25;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009,7,6);
        Entry entry3 = IncomeFactory.createIncome(amount3,entryID3,details3,date3,catID3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        int catID4 = 26;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009,7,7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4,entryID4,details4,date4,catID4);

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryList entryList = EntryListFactory.fromChrono(ll);

        assertEquals(ll.size(),entryList.size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void getReverseChronoIndexOfEntryWithIDTest() {
        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        int catID2 = 24;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001,11,7);
        Entry entry2 = IncomeFactory.createIncome(amount2,entryID2,details2,date2,catID2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        int catID3 = 25;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009,7,6);
        Entry entry3 = IncomeFactory.createIncome(amount3,entryID3,details3,date3,catID3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        int catID4 = 26;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009,7,7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4,entryID4,details4,date4,catID4);

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryList entryList = EntryListFactory.fromChrono(ll);
        assertEquals(entryList.getReverseChronoIndexOfEntryWithID(81), 3);
        assertEquals(entryList.getReverseChronoIndexOfEntryWithID(72), 2);
        assertEquals(entryList.getReverseChronoIndexOfEntryWithID(-7), 1);
        assertEquals(entryList.getReverseChronoIndexOfEntryWithID(6), 0);

        // Try to get the index of an entry that isn't in the list. This test expects
        // IllegalArgumentException, which is exactly what this should throw.
        entryList.getReverseChronoIndexOfEntryWithID(5);
    }
}
