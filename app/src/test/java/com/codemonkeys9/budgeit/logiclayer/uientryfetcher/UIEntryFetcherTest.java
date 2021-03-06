package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UIEntryFetcherTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
    }
    @Test
    public void fetchIncomeWithNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllIncomeEntrys("1999-01-24", "now");
        assertEquals(entryList.size(),1);

        BaseEntry entry1 = entryList.getReverseChrono().get(0);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));
    }

    @Test
    public void fetchAllPurchasesWithNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllPurchaseEntrys("1999-01-24", "now");
        assertEquals(entryList.size(),2);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry4 = entries.get(1);

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));
    }

    @Test
    public void fetchAllEntrysWithNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllEntrys("1999-01-24", "now");
        assertEquals(entryList.size(),3);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry1 = entries.get(2);
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry4 = entries.get(1);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));
    }
    @Test
    public void fetchIncomeWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllIncomeEntrys("1999-01-24", "2019-01-01");
        assertEquals(entryList.size(),1);

        BaseEntry entry1 = entryList.getReverseChrono().get(0);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));
    }

    @Test
    public void fetchAllPurchasesWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllPurchaseEntrys("1999-01-24", "2019-01-01");
        assertEquals(entryList.size(),2);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry4 = entries.get(1);

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));

    }

    @Test
    public void fetchAllEntrysWithDateTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllEntrys("1999-01-24", "2019-01-01");
        assertEquals(entryList.size(),3);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry1 = entries.get(2);
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry4 = entries.get(1);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));
    }
    @Test
    public void fetchAllIncomePastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllIncomeEntrys();
        assertEquals(entryList.size(),2);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry1 = entries.get(0);
        BaseEntry entry3 = entries.get(1);

        assertTrue(amount1.equals(entry1.getAmount()));
        assertTrue(details1.equals(entry1.getDetails()));
        assertTrue(date1.equals((entry1.getDate())));

        assertTrue(amount3.equals(entry3.getAmount()));
        assertTrue(details3.equals(entry3.getDetails()));
        assertTrue(date3.equals((entry3.getDate())));
    }

    @Test
    public void fetchAllPurchasesPastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllPurchaseEntrys();
        assertEquals(entryList.size(),2);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry4 = entries.get(1);

        assertTrue(amount2.equals(entry2.getAmount()));
        assertTrue(details2.equals(entry2.getDetails()));
        assertTrue(date2.equals((entry2.getDate())));

        assertTrue(amount4.equals(entry4.getAmount()));
        assertTrue(details4.equals(entry4.getDetails()));
        assertTrue(date4.equals((entry4.getDate())));

    }

    @Test
    public void fetchAllEntrysPastToNowTest() {
        EntryCreator entryCreator = EntryCreatorFactory.createEntryCreator();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        Amount amount1 = AmountFactory.fromString("100.92");
        Details details1 = DetailsFactory.fromString("Ender was bullied by his older brother Peter");
        Date date1 = DateFactory.fromString("1999-04-23");

        Amount amount2 = AmountFactory.fromString("122.47");
        Details details2 = DetailsFactory.fromString("Ender and his siblings were all some of the smartest children in the world");
        Date date2 = DateFactory.fromString("2000-04-23");

        Amount amount3 = AmountFactory.fromString(".99");
        Details details3 = DetailsFactory.fromString("Ender was selected for a special military program");
        Date date3 = DateFactory.fromString("1999-01-23");

        Amount amount4 = AmountFactory.fromString("30000.00");
        Details details4 = DetailsFactory.fromString("They selected him because, even though he killed a kid that was bullying him" +
                "in self defense, he was appalled by what he had done.");
        Date date4 = DateFactory.fromString("1999-07-23");

        entryCreator.createIncome(amount1,
                details1, date1);
        entryCreator.createPurchase(amount2,
                details2, date2);
        entryCreator.createIncome(amount3,
                details3, date3);
        entryCreator.createPurchase(amount4,
                details4, date4);

        EntryList entryList = entryFetcher.fetchAllEntrys();
        assertEquals(entryList.size(),4);

        List<BaseEntry> entries = entryList.getReverseChrono();
        BaseEntry entry1 = entries.get(2);
        BaseEntry entry2 = entries.get(0);
        BaseEntry entry3 = entries.get(3);
        BaseEntry entry4 = entries.get(1);

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
}
