package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class EntryCreatorTest {
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
    public void createEntryInFutureTest() {
        Database database = DatabaseHolder.getDatabase();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("3000-04-23");

        try{
            entryCreator.createIncome(amount1,
                    details1, date1);
            fail();
        }catch (FutureDateException e){

        }catch(Exception e){
            fail();
        }
    }
    @Test
    public void createOneThenSelectAllTest() {
        Database database = DatabaseHolder.getDatabase();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createIncome(amount1,
                details1, date1);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        List<Entry> entryList = database.selectByDate(interval);
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));
    }
    @Test
    public void createManyThenSelectAllTest() {
        Database database = DatabaseHolder.getDatabase();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        List<Entry> entryList = database.selectByDate(interval);
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(1);
        Entry entry2 = entryList.get(2);
        Entry entry3 = entryList.get(0);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount3.equals(entry3.getAmount()));
        assertTrue(details3.equals(entry3.getDetails()));
        assertTrue(date3.equals((entry3.getDate())));
    }

    // For all create Invalid test, wait for proper parameter validation
    // no use testing if there is no right answer yet
    @Test
    public void createInvalidAmountThenSelectAllTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createIncome(amount1,
                details1, date1);
    }

    @Test
    public void createInvalidDateThenSelectAllTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createIncome(amount1,
                details1, date1);
    }

    @Test
    public void createInvalidDetailsThenSelectAllTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createIncome(amount1,
                details1, date1);
    }
}
