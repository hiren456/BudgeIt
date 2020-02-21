package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

public class EntryFetcherTest {
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
    public void mixedFetchIncomeTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("-122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("-30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createEntry(amount1,
                details1, date1);
        entryCreator.createEntry(amount2,
                details2, date2);
        entryCreator.createEntry(amount3,
                details3, date3);
        entryCreator.createEntry(amount4,
                details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllIncomeEntrys(interval);
        assertEquals(entryList.size(),2);

        List<Entry> entries = entryList.getChrono();
        Entry entry1 = entries.get(1);
        Entry entry3 = entries.get(0);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount3.equals(entry3.getAmount()));
        assertTrue(details3.equals(entry3.getDetails()));
        assertTrue(date3.equals((entry3.getDate())));
    }

    @Test
    public void mixedFetchPurchaseTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("-122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("-30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createEntry(amount1,
                details1, date1);
        entryCreator.createEntry(amount2,
                details2, date2);
        entryCreator.createEntry(amount3,
                details3, date3);
        entryCreator.createEntry(amount4,
                details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllPurchasesEntrys(interval);
        assertEquals(entryList.size(),2);

        List<Entry> entries = entryList.getChrono();
        Entry entry2 = entries.get(1);
        Entry entry4 = entries.get(0);

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));
    }

    @Test
    public void mixedFetchAllTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("-122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("-30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createEntry(amount1,
                details1, date1);
        entryCreator.createEntry(amount2,
                details2, date2);
        entryCreator.createEntry(amount3,
                details3, date3);
        entryCreator.createEntry(amount4,
                details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllEntrys(interval);
        assertEquals(entryList.size(),4);

        List<Entry> entries = entryList.getChrono();
        Entry entry1 = entries.get(1);
        Entry entry2 = entries.get(3);
        Entry entry3 = entries.get(0);
        Entry entry4 = entries.get(2);


        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount3.equals(entry3.getAmount()));
        assertTrue(details3.equals(entry3.getDetails()));
        assertTrue(date3.equals((entry3.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));
    }

    @Test
    public void emptyFetchAllTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllEntrys(interval);
        assertEquals(entryList.size(),0);
    }

    @Test
    public void emptyFetchAllPurchaseTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllPurchasesEntrys(interval);
        assertEquals(entryList.size(),0);
    }

    @Test
    public void emptyFetchAllIncomeTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllIncomeEntrys(interval);
        assertEquals(entryList.size(),0);
    }
}
