package com.codemonkeys9.budgeit.logiclayer;


import com.codemonkeys9.budgeit.entry.Entry;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class LogicLayerTest {
    @Test
    public void fetchIncomeWithNowTest() {
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


        List<Entry> entryList = ll.fetchAllIncomeEntrys("24/01/1999","now");
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());


    }

    @Test
    public void fetchAllPurchasesWithNowTest() {
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


        List<Entry> entryList = ll.fetchAllPurchaseEntrys("24/01/1999","now");
        assertEquals(entryList.size(),2);


        Entry entry2 = entryList.get(1);
        Entry entry4 = entryList.get(0);


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
    public void fetchAllEntrysWithNowTest() {
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


        List<Entry> entryList = ll.fetchAllEntrys("24/01/1999","now");
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(0);
        Entry entry2 = entryList.get(2);
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

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDate());

    }
    @Test
    public void fetchIncomeWithDateTest() {
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


        List<Entry> entryList = ll.fetchAllIncomeEntrys("24/01/1999","01/01/2019");
        assertEquals(entryList.size(),1);

        Entry entry1 = entryList.get(0);


        assertEquals(10092,entry1.getAmount());
        assertTrue("Ender was bullied by his older brother Peter".equals(entry1.getDetails()));
        assertEquals(1999 - 1900,entry1.getDate().getYear());
        assertEquals(4 - 1,entry1.getDate().getMonth());
        assertEquals(23,entry1.getDate().getDate());


    }

    @Test
    public void fetchAllPurchasesWithDateTest() {
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


        List<Entry> entryList = ll.fetchAllPurchaseEntrys("24/01/1999","01/01/2019");
        assertEquals(entryList.size(),2);


        Entry entry2 = entryList.get(1);
        Entry entry4 = entryList.get(0);


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
    public void fetchAllEntrysWithDateTest() {
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


        List<Entry> entryList = ll.fetchAllEntrys("24/01/1999","01/01/2019");
        assertEquals(entryList.size(),3);

        Entry entry1 = entryList.get(0);
        Entry entry2 = entryList.get(2);
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

        assertEquals(-3000000,entry4.getAmount());
        assertTrue(("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.").equals(entry4.getDetails()));
        assertEquals(1999 - 1900,entry4.getDate().getYear());
        assertEquals(07 - 1,entry4.getDate().getMonth());
        assertEquals(23,entry4.getDate().getDate());

    }
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

    @Test
    public void fetchAllPurchasesNoDateTest() {
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


        List<Entry> entryList = ll.fetchAllPurchaseEntrys();
        assertEquals(entryList.size(),2);


        Entry entry2 = entryList.get(1);
        Entry entry4 = entryList.get(0);


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
    public void fetchAllEntrysNoDateTest() {
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


        List<Entry> entryList = ll.fetchAllEntrys();
        assertEquals(entryList.size(),4);

        Entry entry1 = entryList.get(1);
        Entry entry2 = entryList.get(3);
        Entry entry3 = entryList.get(0);
        Entry entry4 = entryList.get(2);

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
    public void calculateTotalIncomeWithDateTest() {
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


        String amount = ll.calculateTotalIncome("0/02/1999","23/03/2000");

        assertTrue(amount.equals("100.92"));
    }

    @Test
    public void calculateTotalIncomeNoDateTest() {
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


        String amount = ll.calculateTotalIncome();

        assertTrue(amount.equals("101.91"));
    }

    @Test
    public void calculateTotalPurchaseWithDateTest() {
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


        String amount = ll.calculateTotalPurchases("0/02/1999","23/03/2000");

        assertTrue(amount.equals("-30000.00"));
    }

    @Test
    public void calculateTotalPurchaseNoDateTest() {
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


        String amount = ll.calculateTotalPurchases();

        assertTrue(amount.equals("-30122.47"));
    }

    @Test
    public void calculateTotalWithDateTest() {
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


        String amount = ll.calculateTotal("0/02/1999","23/03/2000");

        assertTrue(amount.equals("-29899.08"));
    }

    @Test
    public void calculateTotalNoDateTest() {
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


        String amount = ll.calculateTotal();

        assertTrue(amount.equals("-30020.56"));
    }
}
