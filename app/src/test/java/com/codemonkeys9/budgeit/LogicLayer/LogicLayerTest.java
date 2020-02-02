package com.codemonkeys9.budgeit.LogicLayer;


import com.codemonkeys9.budgeit.Entry.Entry;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;



public class LogicLayerTest {
    @Test
    public void fetchAllIncomeNoDateTest() {
        LogicLayer ll = new LogicLayerFactory().createLogicLayer();

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

        ll.createEntry(amount1, details1, date1);
        ll.createEntry(amount2, details2, date2);
        ll.createEntry(amount3, details3, date3);
        ll.createEntry(amount4, details4, date4);


        List<Entry> entryList = ll.fetchAllIncomeEntrys();
        assertEquals(entryList.size(),2);

        Entry entry1 = entryList.get(1);
        Entry entry3 = entryList.get(0);


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
}
