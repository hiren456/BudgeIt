package com.codemonkeys9.budgeit.logiclayer.uicategorycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UICategoryCreatorTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
    }

    @Test
    public void createSavingsCategoryTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "900.00";
        String name = "New Phone";

        uiCategoryCreator.createSavingsCategory(goal,name);

        List<Category> cats = db.getAllCategories();
        Category cat = cats.get(0);

        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat instanceof SavingsCategory);
    }

    @Test
    public void createBudgetCategoryTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "200.00";
        String name = "Food";

        uiCategoryCreator.createBudgetCategory(goal,name);

        List<Category> cats = db.getAllCategories();
        Category cat = cats.get(0);

        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat instanceof BudgetCategory);
    }

    @Test
    public void createTwoBudgetCategoryTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "200.00";
        String name = "Food";

        uiCategoryCreator.createBudgetCategory(goal,name);
        uiCategoryCreator.createBudgetCategory(goal,name);

        List<Category> cats = db.getAllCategories();
        Category cat = cats.get(0);
        Category cat2 = cats.get(1);

        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat instanceof BudgetCategory);

        assertTrue(cat2.getName().getValue().equals(name));
        assertTrue(cat2.getGoal().getDisplay().equals(goal));
        assertTrue(cat2.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat2 instanceof BudgetCategory);
    }

    @Test
    public void createTwoSavingsCategoryTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "200.00";
        String name = "Food";

        uiCategoryCreator.createSavingsCategory(goal,name);
        uiCategoryCreator.createSavingsCategory(goal,name);

        List<Category> cats = db.getAllCategories();
        Category cat = cats.get(0);
        Category cat2 = cats.get(1);

        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat instanceof SavingsCategory);

        assertTrue(cat2.getName().getValue().equals(name));
        assertTrue(cat2.getGoal().getDisplay().equals(goal));
        assertTrue(cat2.getDateLastModified().equals(DateFactory.fromString("now")));
        assertTrue(cat2 instanceof SavingsCategory);
    }
    @Test
    public void createBudgetCategoryInvalidAmountTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "Hello?";
        String name = "Food";
        try{

            uiCategoryCreator.createBudgetCategory(goal,name);
            fail();
        }catch (InvalidAmountException e){

        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void createSavingsCategoryInvalidAmountTest(){
        UICategoryCreator uiCategoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        Database db = DatabaseHolder.getDatabase();

        String goal = "Hello?";
        String name = "Food";
        try{

            uiCategoryCreator.createSavingsCategory(goal,name);
            fail();
        }catch (InvalidAmountException e){

        }catch (Exception e){
            fail();
        }
    }
}
