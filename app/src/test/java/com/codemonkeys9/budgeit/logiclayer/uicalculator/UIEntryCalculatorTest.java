package com.codemonkeys9.budgeit.logiclayer.uicalculator;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcherFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UIEntryCalculatorTest {
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
    public void calculateTotalIncomeWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("1999-02-01", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllIncomeEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        System.out.println(amount);
        assertTrue(amount.equals("100.92"));
    }

    @Test
    public void calculateTotalIncomePastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("1999-02-01", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllPurchasesEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-30000.00"));
    }

    @Test
    public void calculateTotalPurchasePastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);
        entryCreator.createEntry(amount4, details4, date4);

        DateInterval interval = DateIntervalFactory.fromString("1999-02-01", "2000-03-23");
        EntryList entries = entryFetcher.fetchAllEntrys(interval);
        String amount = entryCalculator.sumEntryList(entries).getDisplay();

        assertTrue(amount.equals("-29899.08"));
    }

    @Test
    public void calculateTotalPastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        EntryFetcher entryFetcher = EntryFetcherFactory.createEntryFetcher();
        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();

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
