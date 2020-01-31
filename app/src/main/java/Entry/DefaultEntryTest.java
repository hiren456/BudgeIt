package Entry;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DefaultEntryTest {
    @Test
    public void ValidEntryGetAmountTest() {
        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);

        //test getAmount
        DefaultEntry entry = new DefaultEntry(amount, entryID, details, date);
        assertEquals(entry.getAmount(), 999);
    }

    @Test
    public void ValidEntryGetEntryIDTest() {
        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);


        DefaultEntry entry = new DefaultEntry(amount, entryID, details, date);

        assertEquals(entry.getEntryID(),42);
    }

    @Test
    public void ValidEntryGetDetailsTest() {
        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);
        String details2 = "A very creative description";

        DefaultEntry entry = new DefaultEntry(amount, entryID, details, date);
        assertTrue(details2.equals(entry.getDetails()));
    }

    @Test
    public void ValidEntryGetDateTest() {
        //Create valid Entry
        int amount = 999;
        int entryID = 42;
        String details = "A very creative description";
        Date date = new Date(1999,04,23);
        Date date2 = new Date(1999,04,23);


        DefaultEntry entry = new DefaultEntry(amount, entryID, details, date);
        assertTrue(date2.equals(entry.getDate()));
    }
}
