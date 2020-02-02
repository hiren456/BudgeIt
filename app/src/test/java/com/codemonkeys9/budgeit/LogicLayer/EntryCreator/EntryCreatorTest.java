package com.codemonkeys9.budgeit.LogicLayer.EntryCreator;

import com.codemonkeys9.budgeit.Database.Database;
import com.codemonkeys9.budgeit.Database.DatabaseFactory;
import com.codemonkeys9.budgeit.Entry.Entry;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EntryCreatorTest {

    @Test
    public void createOneThenSelectAllTest() {
        Database database = new DatabaseFactory().createDatabase(0);
        EntryCreator entryCreator = new EntryCreatorFactory().createEntryCreator(database);

        String amount1 = "100.92";
        String details1 = "Ender was bullied by his older brother Peter";
        String date1 = "23/04/1999";

        entryCreator.createEntry(amount1,details1,date1);

        List<Entry> entryList = database.selectByDate(new Date(0),new Date());
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);
        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());
    }
}
