package com.codemonkeys9.budgeit.database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.dateinterval.DateIntervalFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriodFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncomeFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest{
    Database db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createTestableDatabase(
                context,idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
        db = DatabaseHolder.getDatabase();
    }

    @After
    public void closeDb() throws IOException {
        db.clean();
        db.close();
    }

    @Test
    public void updateDateLastCheckedForRecurringTest(){
        Date newDate = DateFactory.fromString("1999-04-23");

        assertTrue(this.db.updateDateLastChecked("Recurring Entry",newDate));
        assertTrue(newDate.equals(this.db.getDateLastChecked("Recurring Entry")));
    }

    @Test
    public void updateDateLastCheckedForCategoryTest(){
        Date newDate = DateFactory.fromString("1999-04-23");

        assertTrue(this.db.updateDateLastChecked("Category Period",newDate));
        assertTrue(newDate.equals(this.db.getDateLastChecked("Category Period")));
    }

    @Test
    public void initialDateLastCheckedTest(){
        Date now = DateFactory.fromString("now");

        assertTrue(now.equals(this.db.getDateLastChecked("Recurring Entry")));
        assertTrue(now.equals(this.db.getDateLastChecked("Category Period")));
    }

    @Test
    public void idCounterInitialValueTest() {

        assertEquals("When a database is initialized, " +
                "the initial value for the id counter, passed as a parameter," +
                "is not what is returned by getIDCounter.", 1, db.getIDCounter("Entry"));

        assertEquals("When a database is initialized, " +
                "the initial value for the id counter, passed as a parameter," +
                "is not what is returned by getIDCounter.", 1, db.getIDCounter("Category"));
    }

    @Test
    public void idCounterUpdateValueTest() {
        db.updateIDCounter("Entry", 23);
        db.updateIDCounter("Category", 14);

        assertEquals("When updateIDCounter is called, " +
                "the id counter returned by getIDCounter" +
                "is not the same as what was passed to updateIDCounter.", db.getIDCounter("Entry"), 23);

        assertEquals("When updateIDCounter is called, " +
                "the id counter returned by getIDCounter" +
                "is not the same as what was passed to updateIDCounter.", db.getIDCounter("Category"), 14);

    }

    @Test
    public void insertThenSelectTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to each other");
        Date date1 = DateFactory.fromInts(2002, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //insert it into the database
        db.insertDefaultEntry(entry1);

        BaseEntry retEntry1 = db.selectDefaultEntryByID(81);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return an entry using selecBYID", retEntry1);
        assertTrue("Database returns a entry with the wrong amount using selectByID"
                , retEntry1.getAmount().equals(amount1));
        assertEquals("Database returns a entry with the wrong entryID using selectByID"
                , entryID1, retEntry1.getEntryID());
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry1.getCatID());
        assertTrue("Database returns a entry with the wrong details string using selectByID"
                , details1.equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID"
                , date1.equals(retEntry1.getDate()));
        assertTrue("Database returns a wrong entry type"
                , retEntry1 instanceof Income);

    }

    @Test
    public void insertManyThenSelectByIDTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID1);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID1);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4, entryID4, details4, date4, catID);

        //insert them into the database
        db.insertCategory(category);
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);
        db.insertDefaultEntry(entry4);


        BaseEntry retEntry2 = db.selectDefaultEntryByID(72);
        BaseEntry retEntry3 = db.selectDefaultEntryByID(-7);
        BaseEntry retEntry4 = db.selectDefaultEntryByID(6);

        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 2", retEntry2);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 3", retEntry3);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 4", retEntry4);

        // test that retEntry2 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount2.equals(retEntry2.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 72, retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details2.equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date2.equals(retEntry2.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID1, retEntry2.getCatID());


        // test that retEntry3 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount3.equals(retEntry3.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , -7, retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details3.equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date3.equals(retEntry3.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry3.getCatID());


        // test that retEntry4 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount4.equals(retEntry4.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 6, retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details4.equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date4.equals(retEntry4.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry4.getCatID());

    }


    @Test
    public void insertManyThenSelectRecurringByIDTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID1);

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID1, period1);

        //Create valid RecurringEntry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        RecurrencePeriod period2 = RecurrencePeriodFactory.createRecurrencePeriod(10, 0, 0, 0);
        RecurringEntry entry2 = RecurringIncomeFactory.createRecurringIncome(amount2, entryID2, details2, date2, catID1, period2);

        //Create valid RecurringEntry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        RecurrencePeriod period3 = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 3, 4);
        RecurringEntry entry3 = RecurringIncomeFactory.createRecurringIncome(amount3, entryID3, details3, date3, catID, period3);

        //Create valid RecurringEntry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        RecurrencePeriod period4 = RecurrencePeriodFactory.createRecurrencePeriod(4, 2, 3, 1);
        RecurringEntry entry4 = RecurringIncomeFactory.createRecurringIncome(amount4, entryID4, details4, date4, catID, period4);

        //insert them into the database
        db.insertCategory(category);
        db.insertRecurringEntry(entry1);
        db.insertRecurringEntry(entry2);
        db.insertRecurringEntry(entry3);
        db.insertRecurringEntry(entry4);


        RecurringEntry retEntry2 = db.selectRecurringEntryByID(72);
        RecurringEntry retEntry3 = db.selectRecurringEntryByID(-7);
        RecurringEntry retEntry4 = db.selectRecurringEntryByID(6);

        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 2", retEntry2);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 3", retEntry3);
        assertNotNull("Database returns null when it should return an entry using selecByID with many inserts 4", retEntry4);

        // test that retEntry2 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount2.equals(retEntry2.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 72, retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details2.equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date2.equals(retEntry2.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID1, retEntry2.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry2.getRecurrencePeriod().equals(period2)));


        // test that retEntry3 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount3.equals(retEntry3.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , -7, retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details3.equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date3.equals(retEntry3.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry3.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry3.getRecurrencePeriod().equals(period3)));


        // test that retEntry4 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount4.equals(retEntry4.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 6, retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details4.equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date4.equals(retEntry4.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry4.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry4.getRecurrencePeriod().equals(period4)));

    }

    @Test
    public void insertManyThenSelectByDateTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID1);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID1);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4, entryID4, details4, date4, catID);

        //insert them into the database
        db.insertCategory(category);
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);
        db.insertDefaultEntry(entry4);

        DateInterval interval = DateIntervalFactory.fromDate(
                DateFactory.fromInts(2001, 10, 7),
                DateFactory.fromInts(2009, 7, 7)
        );

        List<BaseEntry> retList = db.selectDefaultEntriesByDate(interval);

        // test that we got what was expected
        assertEquals("Expected select by date to return 3 entrys but it does not", 3, retList.size());

        BaseEntry retEntry2 = retList.get(0);
        BaseEntry retEntry3 = retList.get(1);
        BaseEntry retEntry4 = retList.get(2);

        // test that retEntry2 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount2.equals(retEntry2.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 72, retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details2.equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date2.equals(retEntry2.getDate()));


        // test that retEntry3 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount3.equals(retEntry3.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , -7, retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details3.equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date3.equals(retEntry3.getDate()));


        // test that retEntry4 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount4.equals(retEntry4.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 6, retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details4.equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date4.equals(retEntry4.getDate()));

    }


    @Test
    public void insertManyThenSelectByDateRecurringTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID1);

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID1, period1);
        assertEquals(entry1.getEntryID(),entryID1);

        //Create valid RecurringEntry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        RecurrencePeriod period2 = RecurrencePeriodFactory.createRecurrencePeriod(10, 0, 0, 0);
        RecurringEntry entry2 = RecurringIncomeFactory.createRecurringIncome(amount2, entryID2, details2, date2, catID1, period2);

        //Create valid RecurringEntry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        RecurrencePeriod period3 = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 3, 4);
        RecurringEntry entry3 = RecurringIncomeFactory.createRecurringIncome(amount3, entryID3, details3, date3, 0, period3);

        //Create valid RecurringEntry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        RecurrencePeriod period4 = RecurrencePeriodFactory.createRecurrencePeriod(4, 2, 3, 1);
        RecurringEntry entry4 = RecurringIncomeFactory.createRecurringIncome(amount4, entryID4, details4, date4, catID, period4);

        //insert them into the database
        db.insertCategory(category);
        db.insertRecurringEntry(entry1);
        assertNotEquals(entry1.getEntryID(),entry2.getEntryID());
        db.insertRecurringEntry(entry2);
        db.insertRecurringEntry(entry3);
        db.insertRecurringEntry(entry4);


        DateInterval interval = DateIntervalFactory.fromDate(
                DateFactory.fromInts(2001, 10, 7),
                DateFactory.fromInts(2009, 7, 7)
        );

        List<RecurringEntry> retList = db.selectRecurringEntriesByDate(interval);

        // test that we got what was expected
        assertEquals("Expected select by date to return 3 entrys but it does not", 3, retList.size());

        RecurringEntry retEntry2 = retList.get(0);
        RecurringEntry retEntry3 = retList.get(1);
        RecurringEntry retEntry4 = retList.get(2);

        // test that retEntry2 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount2.equals(retEntry2.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 72, retEntry2.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details2.equals(retEntry2.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date2.equals(retEntry2.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID1, retEntry2.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry2.getRecurrencePeriod().equals(period2)));


        // test that retEntry3 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount3.equals(retEntry3.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , -7, retEntry3.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details3.equals(retEntry3.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date3.equals(retEntry3.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry3.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry3.getRecurrencePeriod().equals(period3)));


        // test that retEntry4 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount4.equals(retEntry4.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 6, retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details4.equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date4.equals(retEntry4.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry4.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry4.getRecurrencePeriod().equals(period4)));


    }


    @Test
    public void deleteOneEntryTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //insert the entry into the database
        db.insertDefaultEntry(entry1);

        //delete the entry from database
        boolean isDeleted = db.deleteDefaultEntry(81);
        BaseEntry retEntry = db.selectDefaultEntryByID(81);

        assertTrue("Database returns wrong result of deletion", isDeleted);
        assertNull("Database did not delete the entry", retEntry);

    }

    @Test
    public void deleteManyEntriesTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4, entryID4, details4, date4, catID);

        //insert entries into the database
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);
        db.insertDefaultEntry(entry4);

        //delete the entry from database
        boolean isDeleted2 = db.deleteDefaultEntry(72);
        boolean isDeleted3 = db.deleteDefaultEntry(-7);

        //select entries from the database
        BaseEntry retEntry1 = db.selectDefaultEntryByID(81);
        BaseEntry retEntry2 = db.selectDefaultEntryByID(72);
        BaseEntry retEntry3 = db.selectDefaultEntryByID(-7);
        BaseEntry retEntry4 = db.selectDefaultEntryByID(6);

        assertEquals("Database returns wrong entry ID", 81, retEntry1.getEntryID()); //1 entry

        assertTrue("Database returns wrong result of deletion", isDeleted2); //2 entry
        assertNull("Database did not delete the entry", retEntry2);
        assertTrue("Database returns wrong result of deletion", isDeleted3); //3 entry
        assertNull("Database did not delete the entry", retEntry3);

        assertEquals("Database returns wrong entry ID", 6, retEntry4.getEntryID()); //4 entry

    }

    @Test
    public void deleteAllTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        Entry entry4 = PurchaseFactory.createPurchase(amount4, entryID4, details4, date4, catID);

        //insert entries into the database
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);
        db.insertDefaultEntry(entry4);

        //delete the entry from database
        boolean isDeleted1 = db.deleteDefaultEntry(81);
        boolean isDeleted2 = db.deleteDefaultEntry(72);
        boolean isDeleted3 = db.deleteDefaultEntry(-7);
        boolean isDeleted4 = db.deleteDefaultEntry(6);

        //select entries from the database
        BaseEntry retEntry1 = db.selectDefaultEntryByID(81);
        BaseEntry retEntry2 = db.selectDefaultEntryByID(72);
        BaseEntry retEntry3 = db.selectDefaultEntryByID(-7);
        BaseEntry retEntry4 = db.selectDefaultEntryByID(6);

        assertTrue("Database returns wrong result of deletion", isDeleted1);
        assertNull("Database did not delete the entry", retEntry1);
        assertTrue("Database returns wrong result of deletion", isDeleted2);
        assertNull("Database did not delete the entry", retEntry2);
        assertTrue("Database returns wrong result of deletion", isDeleted3);
        assertNull("Database did not delete the entry", retEntry3);
        assertTrue("Database returns wrong result of deletion", isDeleted4);
        assertNull("Database did not delete the entry", retEntry4);

    }


    @Test
    public void deleteRecurringTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID1);

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID1, period1);

        //Create valid RecurringEntry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        RecurrencePeriod period2 = RecurrencePeriodFactory.createRecurrencePeriod(10, 0, 0, 0);
        RecurringEntry entry2 = RecurringIncomeFactory.createRecurringIncome(amount2, entryID2, details2, date2, catID1, period2);

        //Create valid RecurringEntry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = -7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        RecurrencePeriod period3 = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 3, 4);
        RecurringEntry entry3 = RecurringIncomeFactory.createRecurringIncome(amount3, entryID3, details3, date3, catID, period3);

        //Create valid RecurringEntry4
        Amount amount4 = AmountFactory.fromInt(724);
        int entryID4 = 6;
        Details details4 = DetailsFactory.fromString("Ender's game is an interesting book");
        Date date4 = DateFactory.fromInts(2009, 7, 7);
        RecurrencePeriod period4 = RecurrencePeriodFactory.createRecurrencePeriod(4, 2, 3, 1);
        RecurringEntry entry4 = RecurringIncomeFactory.createRecurringIncome(amount4, entryID4, details4, date4, catID, period4);

        //insert them into the database
        db.insertCategory(category);
        db.insertRecurringEntry(entry1);
        db.insertRecurringEntry(entry2);
        db.insertRecurringEntry(entry3);
        db.insertRecurringEntry(entry4);

        //delete the entry from database
        boolean isDeleted1 = db.deleteRecurringEntry(81);
        boolean isDeleted2 = db.deleteRecurringEntry(72);
        boolean isDeleted3 = db.deleteRecurringEntry(-7);

        //select entries from the database
        RecurringEntry retEntry1 = db.selectRecurringEntryByID(81);
        RecurringEntry retEntry2 = db.selectRecurringEntryByID(72);
        RecurringEntry retEntry3 = db.selectRecurringEntryByID(-7);
        RecurringEntry retEntry4 = db.selectRecurringEntryByID(6);

        assertTrue("Database returns wrong result of deletion", isDeleted1);
        assertNull("Database did not delete the entry", retEntry1);
        assertTrue("Database returns wrong result of deletion", isDeleted2);
        assertNull("Database did not delete the entry", retEntry2);
        assertTrue("Database returns wrong result of deletion", isDeleted3);
        assertNull("Database did not delete the entry", retEntry3);


        // test that retEntry4 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount4.equals(retEntry4.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , 6, retEntry4.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details4.equals(retEntry4.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date4.equals(retEntry4.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry4.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry4.getRecurrencePeriod().equals(period4)));


        //empty the db
        boolean isDeleted4 = db.deleteRecurringEntry(6);
        retEntry4 = db.selectRecurringEntryByID(6);

        assertTrue("Database returns wrong result of deletion", isDeleted4);
        assertNull("Database did not delete the entry", retEntry4);

    }

    @Test
    public void selectFromEmptyTest() {

        //select an entry from the database
        BaseEntry retEntry1 = db.selectDefaultEntryByID(81);

        assertNull(retEntry1);
    }

    @Test
    public void deleteFromEmptyTest() {

        //select an entry from the db
        boolean isDeleted1 = db.deleteDefaultEntry(6);

        assertFalse(isDeleted1);
    }

    @Test
    public void selectByDateFromEmptyTest() {

        //select an entry List from the database
        DateInterval interval = DateIntervalFactory.fromDate(
                DateFactory.fromInts(2001, 10, 7),
                DateFactory.fromInts(2009, 7, 7)
        );
        List<BaseEntry> retList = db.selectDefaultEntriesByDate(interval);

        assertEquals("List is not empty", 0, retList.size());
    }


    @Test
    public void selectFromEmptyRecurringTest() {

        //select a recurring entry from the database
        RecurringEntry retEntry1 = db.selectRecurringEntryByID(81);

        assertNull(retEntry1);
    }

    @Test
    public void deleteRecurringFromEmptyTest() {

        //select an entry from the db
        boolean isDeleted1 = db.deleteRecurringEntry(6);

        assertFalse(isDeleted1);
    }

    @Test
    public void selectRecurringByDateFromEmptyTest() {

        //select an entry List from the database
        DateInterval interval = DateIntervalFactory.fromDate(
                DateFactory.fromInts(2001, 10, 7),
                DateFactory.fromInts(2009, 7, 7)
        );
        List<RecurringEntry> retList = db.selectRecurringEntriesByDate(interval);

        assertEquals("List of recurrent entries is not empty", 0, retList.size());
    }

    @Test
    public void updateThenSelectEntryTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 42;
        Details details1 = DetailsFactory.fromString("Tutor");
        Date date1 = DateFactory.fromInts(2016, 7, 7);

        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //insert it into the database
        db.insertDefaultEntry(entry1);

        //update an entry
        Amount updatedAmount = AmountFactory.fromInt(60);
        Details updatedDetails = DetailsFactory.fromString("Not a tutor");
        Date updatedDate = DateFactory.fromInts(2017, 3, 4);

        entry1 = entry1.modifyEntry(updatedAmount, updatedDetails, updatedDate);
        boolean isUpdated = db.updateDefaultEntry(entry1);

        BaseEntry retEntry1 = db.selectDefaultEntryByID(entryID1);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return an entry using selecBYID", retEntry1);

        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , updatedAmount.equals(retEntry1.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , entryID1, retEntry1.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , updatedDetails.equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , updatedDate.equals(retEntry1.getDate()));
        assertTrue(isUpdated);
    }


    @Test
    public void updateThenSelectRecurringEntryTest() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID, period1);

        //insert it into the database
        db.insertRecurringEntry(entry1);

        //update an entry
        Amount updatedAmount = AmountFactory.fromInt(60);
        Details updatedDetails = DetailsFactory.fromString("Not a tutor");
        Date updatedDate = DateFactory.fromInts(2017, 3, 4);
        RecurrencePeriod updatedPeriod = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 3, 4);

        //modify this recurring entry
        entry1 = ((RecurringIncome) entry1.modifyEntry(updatedAmount, updatedDetails, updatedDate));
        entry1 = entry1.changeRecurrencePeriod(updatedPeriod);
        boolean isUpdated = db.updateRecurringEntry(entry1);

        RecurringEntry retEntry1 = db.selectRecurringEntryByID(entryID1);


        // test that it is the one we want
        assertNotNull("Database returns null when it should return an entry using selecBYID", retEntry1);

        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , updatedAmount.equals(retEntry1.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , entryID1, retEntry1.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , updatedDetails.equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , updatedDate.equals(retEntry1.getDate()));
        assertTrue(isUpdated);
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry1.getRecurrencePeriod().equals(updatedPeriod)));
    }

    @Test
    public void updateNotExistedEntryTest() {

        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 42;
        int catID1 = 20;
        Details details1 = DetailsFactory.fromString("Tutor");
        Date date1 = DateFactory.fromInts(2016, 7, 7);

        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID1);

        //update an entry
        boolean isUpdated = db.updateDefaultEntry(entry1);
        BaseEntry retEntry1 = db.selectDefaultEntryByID(entryID1);

        assertNull("Database should not contain the entry, but it does", retEntry1);
        assertFalse("Database is updated, but should not", isUpdated);
    }


    @Test
    public void updateNotExistedRecurringEntryTest() {

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 31;
        int catID1 = 20;
        Details details1 = DetailsFactory.fromString("Big tasty burger");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID1, period1);

        //update an entry
        boolean isUpdated = db.updateRecurringEntry(entry1);
        RecurringEntry retEntry1 = db.selectRecurringEntryByID(entryID1);

        assertNull("Database should not contain the entry, but it does", retEntry1);
        assertFalse("Database is updated, but should not", isUpdated);

    }


    @Test(expected = RuntimeException.class)
    public void insertTwoTimesSameEntryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);

        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 42;
        Details details1 = DetailsFactory.fromString("Tutor");
        Date date1 = DateFactory.fromInts(2016, 7, 7);

        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //insert it into the database
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry1);

    }


    @Test(expected = RuntimeException.class)
    public void insertTwoTimesSameRecurringEntryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(50);
        int entryID1 = 31;
        Details details1 = DetailsFactory.fromString("Big tasty burger");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID, period1);

        //insert it into the database
        db.insertRecurringEntry(entry1);
        db.insertRecurringEntry(entry1);

    }


    @Test
    public void insertThenSelectCategoryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);

        Category retCat = db.selectCategoryByID(catID);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return a category using selecCategoryBYID", retCat);
        assertTrue("Database returns a entry with the wrong amount using selectByID"
                , retCat.getGoal().equals(goal));
        assertEquals("Database returns a category with the wrong catID using selectCategoryByID", catID, retCat.getID());
        assertTrue("Database returns a category with the wrong name string using selectCategoryByID"
                , name.equals(retCat.getName()));
        assertTrue("Database returns a category with the wrong date using selectCategoryByID"
                , date.equals(retCat.getDateLastModified()));

    }


    @Test
    public void insertManyThenSelectCategoryTest() {

        //Create valid categories
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //Create valid categories
        Amount goal1 = AmountFactory.fromInt(3000);
        int catID1 = 3;
        Details name1 = DetailsFactory.fromString("Purchase may 2017");
        Date date1 = DateFactory.fromInts(2017, 4, 20);
        Category category1 = BudgetCategoryFactory.createBudgetCategory(name1, goal1, date1, catID1);

        //Create valid categories
        Amount goal2 = AmountFactory.fromInt(1500);
        int catID2 = 7;
        Details name2 = DetailsFactory.fromString("Savings march 2016");
        Date date2 = DateFactory.fromInts(2016, 5, 20);
        Category category2 = SavingsCategoryFactory.createSavingsCategory(name2, goal2, date2, catID2);

        //insert it into the database
        db.insertCategory(category);
        db.insertCategory(category1);
        db.insertCategory(category2);

        Category retCat = db.selectCategoryByID(catID);
        Category retCat1 = db.selectCategoryByID(catID1);
        Category retCat2 = db.selectCategoryByID(catID2);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return a category using selecCategoryBYID", retCat);
        assertTrue("Database returns a entry with the wrong amount using selectByID", retCat.getGoal().equals(goal));
        assertEquals("Database returns a category with the wrong catID using selectCategoryByID", catID, retCat.getID());
        assertTrue("Database returns a category with the wrong name string using selectCategoryByID", name.equals(retCat.getName()));
        assertTrue("Database returns a category with the wrong date using selectCategoryByID", date.equals(retCat.getDateLastModified()));

        // test that it is the one we want
        assertNotNull("Database returns null when it should return a category using selecCategoryBYID", retCat1);
        assertTrue("Database returns a entry with the wrong amount using selectByID", retCat1.getGoal().equals(goal1));
        assertEquals("Database returns a category with the wrong catID using selectCategoryByID", catID1, retCat1.getID());
        assertTrue("Database returns a category with the wrong name string using selectCategoryByID", name1.equals(retCat1.getName()));
        assertTrue("Database returns a category with the wrong date using selectCategoryByID", date1.equals(retCat1.getDateLastModified()));

        // test that it is the one we want
        assertNotNull("Database returns null when it should return a category using selecCategoryBYID", retCat2);
        assertTrue("Database returns a entry with the wrong amount using selectByID", retCat2.getGoal().equals(goal2));
        assertEquals("Database returns a category with the wrong catID using selectCategoryByID", catID2, retCat2.getID());
        assertTrue("Database returns a category with the wrong name string using selectCategoryByID", name2.equals(retCat2.getName()));
        assertTrue("Database returns a category with the wrong date using selectCategoryByID", date2.equals(retCat2.getDateLastModified()));

    }

    @Test
    public void deleteCategoryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);

        //delete category form db
        boolean isDeleted = db.deleteCategory(catID);
        Category retCat = db.selectCategoryByID(catID);

        // test that it is the one we want
        assertNull("Database should return null after deletion, but it does not", retCat);
        assertTrue("Database returns wrong result of deletion", isDeleted);

    }

    @Test
    public void insertEntryDefaultCategory() {
        //Create valid Entry
        Amount amount1 = AmountFactory.fromInt(7249);

        IDManager manager = IDManagerFactory.createIDManager();

        int entryID1 = 81;
        int catID1 = manager.getDefaultID("Category");
        Details details1 = DetailsFactory.fromString("Some letters put next to each other");
        Date date1 = DateFactory.fromInts(2002, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID1);

        //insert it into the database
        db.insertDefaultEntry(entry1);

        BaseEntry retEntry1 = db.selectDefaultEntryByID(81);

        // test that it is the one we want
        assertNotNull("Database returns null when it should return a category using selectCategoryBYID", retEntry1);
        assertTrue("Database returns a entry with the wrong amount using selectByID"
                , retEntry1.getAmount().equals(amount1));
        assertEquals("Database returns a entry with the wrong entryID using selectByID"
                , entryID1, retEntry1.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID"
                , details1.equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID"
                , date1.equals(retEntry1.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , entryID1, retEntry1.getEntryID());

    }


    @Test
    public void insertRecurringEntryDefaultCategory() {

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int catID = manager.getDefaultID("Category");

        //Create valid RecurringEntry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID, period1);

        //insert it into db
        db.insertRecurringEntry(entry1);

        //select entries from the database
        RecurringEntry retEntry1 = db.selectRecurringEntryByID(81);

        // test that retEntry1 is the one we want
        assertTrue("Database returns a entry with the wrong amount using selectByID with many inserts"
                , amount1.equals(retEntry1.getAmount()));
        assertEquals("Database returns a entry with the wrong entryID using selectByID with many inserts"
                , entryID1, retEntry1.getEntryID());
        assertTrue("Database returns a entry with the wrong details string using selectByID with many inserts"
                , details1.equals(retEntry1.getDetails()));
        assertTrue("Database returns a entry with the wrong date using selectByID with many inserts"
                , date1.equals(retEntry1.getDate()));
        assertEquals("Database returns a entry with the wrong catID using selectByID"
                , catID, retEntry1.getCatID());
        assertTrue("Database returns an entry with the wrong recurrence period using selectByID with many inserts"
                , (retEntry1.getRecurrencePeriod().equals(period1)));

    }

    @Test
    public void selectRecurringEntryByCategoryIDTest() {
        //Create valid category
        Amount goal = AmountFactory.fromInt(10000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchases I did not need");
        Date cDate = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, cDate, catID);

        //Create valid category
        Amount goal1 = AmountFactory.fromInt(15000);
        int catID1 = 21;
        Details name1 = DetailsFactory.fromString("Purchases my wife made");
        Date cDate1 = DateFactory.fromInts(2013, 4, 20);
        Category category1 = BudgetCategoryFactory.createBudgetCategory(name1, goal1, cDate1, catID1);


        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID, period1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        RecurrencePeriod period2 = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 1, 2);
        RecurringEntry entry2 = RecurringIncomeFactory.createRecurringIncome(amount2, entryID2, details2, date2, catID, period2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = 7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        RecurrencePeriod period3 = RecurrencePeriodFactory.createRecurrencePeriod(2, 1, 2, 1);
        RecurringEntry entry3 = RecurringIncomeFactory.createRecurringIncome(amount3, entryID3, details3, date3, catID1, period3);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(743);
        int entryID4 = 15;
        Details details4 = DetailsFactory.fromString("I am running out of ideas again");
        Date date4 = DateFactory.fromInts(2012, 7, 6);
        RecurrencePeriod period4 = RecurrencePeriodFactory.createRecurrencePeriod(4, 4, 5, 5);
        RecurringEntry entry4 = RecurringIncomeFactory.createRecurringIncome(amount4, entryID4, details4, date4, catID1, period4);

        //Create valid Entry5
        Amount amount5 = AmountFactory.fromInt(32);
        int entryID5 = 133;
        Details details5 = DetailsFactory.fromString("T-shirt");
        Date date5 = DateFactory.fromInts(2000, 7, 6);
        RecurrencePeriod period5 = RecurrencePeriodFactory.createRecurrencePeriod(5, 5, 4, 4);
        RecurringEntry entry5 = RecurringIncomeFactory.createRecurringIncome(amount5, entryID5, details5, date5, catID1, period5);


        db.insertCategory(category);
        db.insertCategory(category1);

        db.insertRecurringEntry(entry1);
        db.insertRecurringEntry(entry2);
        db.insertRecurringEntry(entry3);
        db.insertRecurringEntry(entry4);
        db.insertRecurringEntry(entry5);

        List<RecurringEntry> entryList1 = db.getRecurringEntriesByCategoryID(catID);
        List<RecurringEntry> entryList2 = db.getRecurringEntriesByCategoryID(catID1);

        assertEquals("There should be 2 entries with catID = 23 ", 2, entryList1.size());
        assertEquals("There should be 3 entries with catID1 = 21", 3, entryList2.size());

    }


    @Test
    public void selectEntryByCategoryIDTest() {
        //Create valid category
        Amount goal = AmountFactory.fromInt(10000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchases I did not need");
        Date cDate = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, cDate, catID);

        //Create valid category
        Amount goal1 = AmountFactory.fromInt(15000);
        int catID1 = 21;
        Details name1 = DetailsFactory.fromString("Purchases my wife made");
        Date cDate1 = DateFactory.fromInts(2013, 4, 20);
        Category category1 = BudgetCategoryFactory.createBudgetCategory(name1, goal1, cDate1, catID1);


        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = 7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID1);

        //Create valid Entry4
        Amount amount4 = AmountFactory.fromInt(743);
        int entryID4 = 15;
        Details details4 = DetailsFactory.fromString("I am running out of ideas again");
        Date date4 = DateFactory.fromInts(2012, 7, 6);
        Entry entry4 = IncomeFactory.createIncome(amount4, entryID4, details4, date4, catID1);

        //Create valid Entry5
        Amount amount5 = AmountFactory.fromInt(32);
        int entryID5 = 133;
        Details details5 = DetailsFactory.fromString("T-shirt");
        Date date5 = DateFactory.fromInts(2000, 7, 6);
        Entry entry5 = IncomeFactory.createIncome(amount5, entryID5, details5, date5, catID1);


        db.insertCategory(category);
        db.insertCategory(category1);

        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);
        db.insertDefaultEntry(entry4);
        db.insertDefaultEntry(entry5);

        List<BaseEntry> entryList1 = db.getDefaultEntriesByCategoryID(catID);
        List<BaseEntry> entryList2 = db.getDefaultEntriesByCategoryID(catID1);

        assertEquals("There should be 2 entries with catID = 23 ", 2, entryList1.size());
        assertEquals("There should be 3 entries with catID1 = 21", 3, entryList2.size());

    }

    @Test
    public void deleteCategoryCheckNewDefaultEntryCatID() {
        //Create valid category
        Amount goal = AmountFactory.fromInt(10000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchases I did not need");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        Entry entry2 = IncomeFactory.createIncome(amount2, entryID2, details2, date2, catID);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = 7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        Entry entry3 = IncomeFactory.createIncome(amount3, entryID3, details3, date3, catID);


        db.insertCategory(category);
        db.insertDefaultEntry(entry1);
        db.insertDefaultEntry(entry2);
        db.insertDefaultEntry(entry3);

        db.deleteCategory(catID);

        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        List<BaseEntry> entryList = db.getDefaultEntriesByCategoryID(catID);
        List<BaseEntry> entryListDefault = db.getDefaultEntriesByCategoryID(defaultCatID);
        List<BaseEntry> entryListExist = db.getAllDefaultEntries();

        assertEquals("There should be 3 entries with default catID", 3, entryListDefault.size());
        assertEquals("There should be 0 entries with catID = 23", 0, entryList.size());
        assertEquals("There should be 3 total entries", 3, entryListExist.size());

    }


    @Test
    public void deleteCategoryCheckNewRecurringEntryCatID() {

        //Create valid category
        Amount goal1 = AmountFactory.fromInt(15000);
        int catID1 = 21;
        Details name1 = DetailsFactory.fromString("Purchases my wife made");
        Date cDate1 = DateFactory.fromInts(2013, 4, 20);
        Category category1 = BudgetCategoryFactory.createBudgetCategory(name1, goal1, cDate1, catID1);


        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID1, period1);

        //Create valid Entry2
        Amount amount2 = AmountFactory.fromInt(520);
        int entryID2 = 72;
        Details details2 = DetailsFactory.fromString("Some letters put next to eachother again");
        Date date2 = DateFactory.fromInts(2001, 11, 7);
        RecurrencePeriod period2 = RecurrencePeriodFactory.createRecurrencePeriod(1, 2, 1, 2);
        RecurringEntry entry2 = RecurringIncomeFactory.createRecurringIncome(amount2, entryID2, details2, date2, catID1, period2);

        //Create valid Entry3
        Amount amount3 = AmountFactory.fromInt(604);
        int entryID3 = 7;
        Details details3 = DetailsFactory.fromString("I am running out of ideas");
        Date date3 = DateFactory.fromInts(2009, 7, 6);
        RecurrencePeriod period3 = RecurrencePeriodFactory.createRecurrencePeriod(2, 1, 2, 1);
        RecurringEntry entry3 = RecurringIncomeFactory.createRecurringIncome(amount3, entryID3, details3, date3, catID1, period3);


        db.insertCategory(category1);
        db.insertRecurringEntry(entry1);
        db.insertRecurringEntry(entry2);
        db.insertRecurringEntry(entry3);

        db.deleteCategory(catID1);

        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        List<RecurringEntry> entryList = db.getRecurringEntriesByCategoryID(catID1);
        List<RecurringEntry> entryListDefault = db.getRecurringEntriesByCategoryID(defaultCatID);
        List<RecurringEntry> entryListExist = db.getAllRecurringEntries();

        assertEquals("There should be 3 entries with default catID", 3, entryListDefault.size());
        assertEquals("There should be 0 entries with catID = 21", 0, entryList.size());
        assertEquals("There should be 3 total entries", 3, entryListExist.size());




    }

    @Test
    public void insertManyThenSelectAllCategoriesTest() {

        //Create valid categories
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //Create valid categories
        Amount goal1 = AmountFactory.fromInt(3000);
        int catID1 = 3;
        Details name1 = DetailsFactory.fromString("Purchase may 2017");
        Date date1 = DateFactory.fromInts(2017, 4, 20);
        Category category1 = BudgetCategoryFactory.createBudgetCategory(name1, goal1, date1, catID1);

        //Create valid categories
        Amount goal2 = AmountFactory.fromInt(1500);
        int catID2 = 7;
        Details name2 = DetailsFactory.fromString("Savings march 2016");
        Date date2 = DateFactory.fromInts(2016, 5, 20);
        Category category2 = SavingsCategoryFactory.createSavingsCategory(name2, goal2, date2, catID2);

        //insert it into the database
        db.insertCategory(category);
        db.insertCategory(category1);
        db.insertCategory(category2);

        List <Category> categoryList = db.getAllCategories();

        assertEquals("There should be 3 categories", 3, categoryList.size());
    }

    @Test
    public void updateCategoryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);

        //update entry
        goal = AmountFactory.fromInt(2500);
        name = DetailsFactory.fromString("Purchase march 2016");
        date = DateFactory.fromInts(2016, 3, 12);
        category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //delete category form db
        boolean isUpdated = db.updateCategory(category);
        Category retCat = db.selectCategoryByID(catID);

        // test that we have correct updated category
        assertNotNull("Database should return a category after category is updated, but it does not", retCat);
        assertTrue("updateCategory should return true", isUpdated);
        assertTrue("Database returns a category with the wrong goal", retCat.getGoal().equals(goal));
        assertEquals("Database returns a category with the wrong catID", catID, retCat.getID());
        assertTrue("Database returns a category with the wrong name string", name.equals(retCat.getName()));
        assertTrue("Database returns a category with the wrong date", date.equals(retCat.getDateLastModified()));
    }

    @Test(expected = RuntimeException.class)
    public void insertTwoTimesSameCategoryTest() {

        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //insert it into the database
        db.insertCategory(category);
        db.insertCategory(category);
    }


    @Test(expected = RuntimeException.class)
    public void insertEntryWithNotExistedCategory(){
        //Create valid Entry1
        int catID = 12;
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        db.insertDefaultEntry(entry1); //should throw an exception
    }


    @Test(expected = RuntimeException.class)
    public void insertRecurringEntryWithNotExistedCategory(){
        //Create valid Entry1
        int catID = 12;
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod period1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry entry1 = RecurringIncomeFactory.createRecurringIncome(amount1, entryID1, details1, date1, catID, period1);

        db.insertRecurringEntry(entry1); //should throw an exception
    }


    @Test
    public void testCleanDB(){
        //Create valid category
        Amount goal = AmountFactory.fromInt(2000);
        int catID = 23;
        Details name = DetailsFactory.fromString("Purchase may 2016");
        Date date = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001, 7, 7);
        Entry entry1 = IncomeFactory.createIncome(amount1, entryID1, details1, date1, catID);

        //Create valid RecurringEntry1
        Amount ramount1 = AmountFactory.fromInt(50);
        int rentryID1 = 31;
        Details rdetails1 = DetailsFactory.fromString("Big tasty burger");
        Date rdate1 = DateFactory.fromInts(2001, 7, 7);
        RecurrencePeriod rperiod1 = RecurrencePeriodFactory.createRecurrencePeriod(0, 2, 0, 0);
        RecurringEntry rentry1 = RecurringIncomeFactory.createRecurringIncome(ramount1, rentryID1, rdetails1, rdate1, catID, rperiod1);

        db.insertCategory(category);
        db.insertDefaultEntry(entry1);
        db.insertRecurringEntry(rentry1);

        db.clean();

        List<Category> cats = db.getAllCategories();
        List<BaseEntry> entries = db.getAllDefaultEntries();
        List<RecurringEntry> rentries = db.getAllRecurringEntries();

        assertEquals("There should be 0 Categories", 0, cats.size());
        assertEquals("There should be 0 Default Entries", 0, entries.size());
        assertEquals("There should be 0 Recurring Entries", 0, rentries.size());


    }

    @Test
    public void idCounterWrongIDNameTest() {
        int id = db.getIDCounter("Wrong");
        assertEquals("Returns wrong error counter", -1, id);
    }
}
