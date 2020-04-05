package com.codemonkeys9.budgeit.logiclayer.uientrycategorizer;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UIEntryCategorizerTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));

        //Create valid category
        Amount startGoal = AmountFactory.fromInt(2000);
        int catID1 = 23;
        Details startName = DetailsFactory.fromString("Purchase may 2016");
        Date catDate = DateFactory.fromInts(2016, 4, 20);
        Category category = BudgetCategoryFactory.createBudgetCategory(startName, startGoal, catDate, catID1);
        DatabaseHolder.getDatabase().insertCategory(category);
    }

    @Test
    public void categorizeEntryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name = DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);


        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,23);
        db.insertDefaultEntry(entry1);


        categorizer.categorizeEntry(81,24);
        BaseEntry newEntry = db.selectDefaultEntryByID(81);
        assertEquals(24,newEntry.getCatID());
    }

    @Test
    public void categorizeEntryBadEntryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertDefaultEntry(entry1);

        try{
            categorizer.categorizeEntry(Integer.MAX_VALUE,24);
            fail();
        }catch (EntryDoesNotExistException e) {
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void categorizeEntryBadCategoryTest(){

        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertDefaultEntry(entry1);

        try{
            categorizer.categorizeEntry(81,Integer.MAX_VALUE);
            fail();
        }catch (CategoryDoesNotExistException e) {
        }catch (Exception e){
            fail();
        }
    }
    @Test
    public void CategorizeEntryThenCheckThatDateOfCategoryIsTodayTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertDefaultEntry(entry1);


        categorizer.categorizeEntry(81,24);
        Category newCat = db.selectCategoryByID(24);
        assertTrue(newCat.getDateLastModified().equals(DateFactory.fromString("now")));
    }
}
