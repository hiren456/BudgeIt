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

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EntryCreatorTest {

    @Test
    public void createOneThenSelectAllTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        Database database = DatabaseHolder.getDatabase();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createEntry(amount1, details1, date1);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        List<Entry> entryList = database.selectByDate(interval);
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);
        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDay());
    }
    @Test
    public void createManyThenSelectAllTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        Database database = DatabaseHolder.getDatabase();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("-122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        entryCreator.createEntry(amount1, details1, date1);
        entryCreator.createEntry(amount2, details2, date2);
        entryCreator.createEntry(amount3, details3, date3);

        DateInterval interval = DateIntervalFactory.fromString("past", "now");
        List<Entry> entryList = database.selectByDate(interval);
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(1);
        Entry entry2 = entryList.get(2);
        Entry entry3 = entryList.get(0);

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
    }

    // For all create Invalid test, wait for proper parameter validation
    // no use testing if there is no right answer yet
    @Test
    public void createInvalidAmountThenSelectAllTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createEntry(amount1, details1, date1);
    }

    @Test
    public void createInvalidDateThenSelectAllTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createEntry(amount1, details1, date1);
    }

    @Test
    public void createInvalidDetailsThenSelectAllTest() {
        // TODO: The database should be freshly created each test. DatabaseHolder.init doesn't (and
        //       shouldn't) do that.
        //     - Zach
        DatabaseHolder.init();
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        entryCreator.createEntry(amount1, details1, date1);
    }
}
