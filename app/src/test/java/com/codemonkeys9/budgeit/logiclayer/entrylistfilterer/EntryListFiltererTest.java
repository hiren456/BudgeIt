package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;

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

import static org.junit.Assert.*;

public class EntryListFiltererTest {
    @Test
    public void getPurchasesTest() {
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

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryListFilterer filter = EntryListFiltererFactory.createEntryListFilterer();

        filter.getPurchases(ll);

        assertEquals(1,ll.size());
        assertTrue(ll.get(0).equals(entry4));
    }

    @Test
    public void getIncomeTest() {
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

        LinkedList<Entry> ll = new LinkedList<Entry>();
        ll.add(entry1);
        ll.add(entry2);
        ll.add(entry3);
        ll.add(entry4);

        EntryListFilterer filter = EntryListFiltererFactory.createEntryListFilterer();

        filter.getIncome(ll);

        assertEquals(3,ll.size());
        assertTrue(ll.get(0).equals(entry1));
        assertTrue(ll.get(1).equals(entry2));
        assertTrue(ll.get(2).equals(entry3));
    }
}
