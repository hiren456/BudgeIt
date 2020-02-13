package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.entry.DisplayConverter;
import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParser;
import com.codemonkeys9.budgeit.logiclayer.dateparser.DateParserFactory;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntryCreatorTest {

    @Test
    public void createOneThenSelectAllTest() {
        Database database = DatabaseFactory.createDatabase(0);
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        DateParser dateParser = DateParserFactory.createDateParser();

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount1), details1, dateParser.parseDate(date1));

        List<Entry> entryList = database.selectByDate(new Date(0),new Date());
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);
        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());
    }
    @Test
    public void createManyThenSelectAllTest() {
        Database database = DatabaseFactory.createDatabase(0);
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        DateParser dateParser = DateParserFactory.createDateParser();

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        String amount2 = "-122.47";
        String details2 = "Ender and his siblings were all some of the smartest children in the world";
        String date2 = "23/04/2000";

        String amount3 = ".99";
        String details3 = "Ender was selected for a special military program";
        String date3 = "23/01/1999";

        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount1), details1, dateParser.parseDate(date1));
        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount2), details2, dateParser.parseDate(date2));
        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount3), details3, dateParser.parseDate(date3));

        List<Entry> entryList = database.selectByDate(new Date(0),new Date());
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(1);
        Entry entry2 = entryList.get(2);
        Entry entry3 = entryList.get(0);

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
    }

    // For all create Invalid test, wait for proper parameter validation
    // no use testing if there is no right answer yet
    @Test
    public void createInvalidAmountThenSelectAllTest() {
        Database database = DatabaseFactory.createDatabase(0);
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        DateParser dateParser = DateParserFactory.createDateParser();

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount1), details1, dateParser.parseDate(date1));
    }

    @Test
    public void createInvalidDateThenSelectAllTest() {
        Database database = DatabaseFactory.createDatabase(0);
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        DateParser dateParser = DateParserFactory.createDateParser();

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount1), details1, dateParser.parseDate(date1));
    }

    @Test
    public void createInvalidDetailsThenSelectAllTest() {
        Database database = DatabaseFactory.createDatabase(0);
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator(database);
        DateParser dateParser = DateParserFactory.createDateParser();

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        entryCreator.createEntry(DisplayConverter.parseDisplayAmount(amount1), details1, dateParser.parseDate(date1));
    }
}
