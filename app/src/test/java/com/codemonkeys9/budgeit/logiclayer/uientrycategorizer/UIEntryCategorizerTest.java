package com.codemonkeys9.budgeit.logiclayer.uientrycategorizer;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.entrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.entrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UIEntryCategorizerTest {
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
    public void categorizeEntryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.creatorUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertEntry(entry1);


        categorizer.categorizeEntry(81,24);
        Entry newEntry = db.selectByID(28);
        assertEquals(24,newEntry.getCatID());
    }

    @Test
    public void categorizeEntryBadEntryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.creatorUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertEntry(entry1);

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

        UICategoryCreator creator = UICategoryCreatorFactory.creatorUICategoryCreator();
        UIEntryCategorizer categorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name =DetailsFactory.fromString( "Food");
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,catID);
        db.insertCategory(cat);

        //Create valid Entry1
        Amount amount1 = AmountFactory.fromInt(7249);
        int entryID1 = 81;
        int catID1 = 23;
        Details details1 = DetailsFactory.fromString("Some letters put next to eachother");
        Date date1 = DateFactory.fromInts(2001,7,7);
        Entry entry1 = IncomeFactory.createIncome(amount1,entryID1,details1,date1,catID1);
        db.insertEntry(entry1);

        try{
            categorizer.categorizeEntry(81,Integer.MAX_VALUE);
            fail();
        }catch (EntryDoesNotExistException e) {
        }catch (Exception e){
            fail();
        }
    }
}
