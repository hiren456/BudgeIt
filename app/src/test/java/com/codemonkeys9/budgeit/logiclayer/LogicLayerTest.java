package com.codemonkeys9.budgeit.logiclayer;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogicLayerTest {
    @Test
    public void fetchIncomeWithNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "now");
        EntryList entryList = entryFetcher.fetchAllIncomeEntrys(interval);
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());
    }

    @Test
    public void fetchAllPurchasesWithNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "now");
        EntryList entryList = entryFetcher.fetchAllPurchasesEntrys(interval);
        assertEquals(entryList.size(),2);

        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);


        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());


        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }

    @Test
    public void fetchAllEntrysWithNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "now");
        EntryList entryList = entryFetcher.fetchAllEntrys(interval);
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(2);
        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);

        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());

        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }
    @Test
    public void fetchIncomeWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "2019-01-01");
        EntryList entryList = entryFetcher.fetchAllIncomeEntrys(interval);
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());


    }

    @Test
    public void fetchAllPurchasesWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "2019-01-01");
        EntryList entryList = entryFetcher.fetchAllPurchasesEntrys(interval);
        assertEquals(entryList.size(),2);


        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);


        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());


        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }

    @Test
    public void fetchAllEntrysWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-01-24", "2019-01-01");
        EntryList entryList = entryFetcher.fetchAllEntrys(interval);
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(2);
        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);

        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());

        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }
    @Test
    public void fetchAllIncomePastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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


        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllIncomeEntrys(interval);
        assertEquals(entryList.size(),2);

        Entry entry1 = entryList.get(0);
        Entry entry3 = entryList.get(1);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());


        assertEquals(99,entry3.getAmount());
        assertTrue("Ender was selected for a special military program".equals(entry3.getDetails()));
        assertEquals(1999 - 1900,entry3.getDate().getYear());
        assertEquals(1 - 1,entry3.getDate().getMonth());
        assertEquals(23,entry3.getDate().getDay());

    }

    @Test
    public void fetchAllPurchasesPastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllPurchasesEntrys(interval);
        assertEquals(entryList.size(),2);


        Entry entry2 = entryList.get(0);
        Entry entry4 = entryList.get(1);


        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());


        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }

    @Test
    public void fetchAllEntrysPastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();

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

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entryList = entryFetcher.fetchAllEntrys(interval);
        assertEquals(entryList.size(),4);

        Entry entry1 = entryList.get(2);
        Entry entry2 = entryList.get(0);
        Entry entry3 = entryList.get(3);
        Entry entry4 = entryList.get(1);

        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());

        assertEquals(-12247,entry2.getAmount());
        assertTrue("Ender and his siblings were all some of the smartest children in the world".equals(entry2.getDetails()));
        assertEquals(2000 - 1900,entry2.getDate().getYear());
        assertEquals(4 - 1,entry2.getDate().getMonth());
        assertEquals(23,entry2.getDate().getDay());

        assertEquals(99,entry3.getAmount());
        assertTrue("Ender was selected for a special military program".equals(entry3.getDetails()));
        assertEquals(1999 - 1900,entry3.getDate().getYear());
        assertEquals(1 - 1,entry3.getDate().getMonth());
        assertEquals(23,entry3.getDate().getDay());

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDay());

    }

    @Test
    public void calculateTotalIncomeWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-02-00", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllIncomeEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        System.out.println(amount);
        assertTrue(amount.equals("100.92"));
    }

    @Test
    public void calculateTotalIncomePastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entries = entryFetcher.fetchAllIncomeEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("101.91"));
    }

    @Test
    public void calculateTotalPurchaseWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-02-00", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllPurchasesEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-30000.00"));
    }

    @Test
    public void calculateTotalPurchasePastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entries = entryFetcher.fetchAllPurchasesEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-30122.47"));
    }

    @Test
    public void calculateTotalWithDateTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("1999-02-00", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-29899.08"));
    }

    @Test
    public void calculateTotalPastToNowTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        EntryList entries = entryFetcher.fetchAllEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-30020.56"));
    }
}
