package com.codemonkeys9.budgeit.LogicLayer.EntryFetcher;

import com.codemonkeys9.budgeit.LogicLayer.Database.Database;
import com.codemonkeys9.budgeit.LogicLayer.Database.DatabaseFactory;
import com.codemonkeys9.budgeit.Entry.Entry;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParser;
import com.codemonkeys9.budgeit.LogicLayer.DateParser.DateParserFactory;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreator;
import com.codemonkeys9.budgeit.LogicLayer.EntryCreator.EntryCreatorFactory;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

public class EntryFetcherTest {
    @Test
    public void mixedFetchIncomeTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        String amount2 = "-122.47";
        String details2 = "Ender and his siblings were all some of the smartest children in the world";
        String date2 = "23/04/2000";

        String amount3 = ".99";
        String details3 = "Ender was selected for a special military program";
        String date3 = "23/01/1999";

        String amount4 = "-30000.00";
        String details4 = "They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.";
        String date4 = "23/07/1999";

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);


        List<Entry> entryList = entryFetcher.fetchAllIncomeEntrys("past","now");
        assertEquals(entryList.size(),2);

        Entry entry1 = entryList.get(0);
        Entry entry3 = entryList.get(1);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());

        assertEquals(99,entry3.getAmount());
        assertTrue("Ender was selected for a special military program".equals(entry3.getDetails()));
        assertEquals(1999 - 1900,entry3.getDate().getYear());
        assertEquals(1 - 1,entry3.getDate().getMonth());
        assertEquals(23,entry3.getDate().getDate());
    }

    @Test
    public void mixedFetchPurchaseTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        String amount2 = "-122.47";
        String details2 = "Ender and his siblings were all some of the smartest children in the world";
        String date2 = "23/04/2000";

        String amount3 = ".99";
        String details3 = "Ender was selected for a special military program";
        String date3 = "23/01/1999";

        String amount4 = "-30000.00";
        String details4 = "They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.";
        String date4 = "23/07/1999";

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);


        List<Entry> entryList = entryFetcher.fetchAllPurchasesEntrys("past","now");
        assertEquals(entryList.size(),2);

        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);

        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDate());

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDate());
    }

    @Test
    public void mixedFetchAllTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        String amount2 = "-122.47";
        String details2 = "Ender and his siblings were all some of the smartest children in the world";
        String date2 = "23/04/2000";

        String amount3 = ".99";
        String details3 = "Ender was selected for a special military program";
        String date3 = "23/01/1999";

        String amount4 = "-30000.00";
        String details4 = "They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.";
        String date4 = "23/07/1999";

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);


        List<Entry> entryList = entryFetcher.fetchAllEntrys("past","now");
        assertEquals(entryList.size(),4);

        Entry entry1 = entryList.get(2);
        Entry entry2 = entryList.get(0);
        Entry entry3 = entryList.get(3);
        Entry entry4 = entryList.get(1);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());

        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDate());

        assertEquals(99,entry3.getAmount());
        assertTrue("Ender was selected for a special military program".equals(entry3.getDetails()));
        assertEquals(1999 - 1900,entry3.getDate().getYear());
        assertEquals(1 - 1,entry3.getDate().getMonth());
        assertEquals(23,entry3.getDate().getDate());

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDate());
    }

    @Test
    public void emptyFetchAllTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);


        List<Entry> entryList = entryFetcher.fetchAllEntrys("past","now");
        assertEquals(entryList.size(),0);
    }

    @Test
    public void emptyFetchAllPurchaseTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);


        List<Entry> entryList = entryFetcher.fetchAllPurchasesEntrys("past","now");
        assertEquals(entryList.size(),0);
    }

    @Test
    public void emptyFetchAllIncomeTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        DateParser dateParser = new DateParserFactory().createDateParser();
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);
        EntryFetcher entryFetcher = new EntryFetcherFactory().createEntryFetcher(database,dateParser);


        List<Entry> entryList = entryFetcher.fetchAllIncomeEntrys("past","now");
        assertEquals(entryList.size(),0);
    }
}
