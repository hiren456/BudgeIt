package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UICategoryModifierTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));

        Database db = DatabaseHolder.getDatabase();

        Amount goal = AmountFactory.fromString( "200.00");
        Details name = DetailsFactory.fromString( "Food");
        Date date = DateFactory.fromInts(1999,04,23);
        int catID = 24;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,catID);
        db.insertCategory(cat);

        Amount goal2 = AmountFactory.fromString( "7000.00");
        Details name2 = DetailsFactory.fromString( "Phone");
        Date date2 = DateFactory.fromInts(1999,04,23);
        int catID2 = 25;
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name2,goal2,date2,catID2);
        db.insertCategory(cat2);
    }

    @Test
    public void deleteCategoryTest(){
        UICategoryModifier mod =  UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        mod.deleteCategory(24);

        assertNull(db.selectCategoryByID(24));
        assertNotNull(db.selectCategoryByID(25));
    }

    @Test
    public void deleteNonExistentCategoryTest(){
        UICategoryModifier mod =  UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        try{
            mod.deleteCategory(Integer.MIN_VALUE);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e ){
            fail();
        }
    }

    @Test
    public void changeGoalCategoryTest() {
        UICategoryModifier mod = UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        Category oldCat =  db.selectCategoryByID(24);
        Amount newAmount = AmountFactory.fromInt(50000);
        mod.changeGoal(24,newAmount);
        Category newCat =  db.selectCategoryByID(24);

        assertTrue(oldCat.getDateLastModified().equals(newCat.getDateLastModified()));
        assertTrue(oldCat.getName().equals(newCat.getName()));
        assertTrue(newAmount.equals(newCat.getGoal()));
    }

    @Test
    public void changeGoalNonExistentCategoryTest() {
        UICategoryModifier mod = UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        Amount newAmount = AmountFactory.fromInt(50000);

        try{
            mod.changeGoal(Integer.MIN_VALUE,newAmount);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void changeNameCategoryTest() {
        UICategoryModifier mod = UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        Category oldCat =  db.selectCategoryByID(24);
        Details newName = DetailsFactory.fromString("Better Food");
        mod.changeName(24,newName);
        Category newCat =  db.selectCategoryByID(24);

        assertTrue(oldCat.getDateLastModified().equals(newCat.getDateLastModified()));
        assertTrue(newName.equals(newCat.getName()));
        assertTrue(oldCat.getGoal().equals(newCat.getGoal()));
    }

    @Test
    public void changeNameNonExistentCategoryTest() {
        UICategoryModifier mod = UICategoryModifierFactory.createUICategoryModifier();
        Database db = DatabaseHolder.getDatabase();

        Details newName = DetailsFactory.fromString("Better Food");

        try{
            mod.changeName(Integer.MIN_VALUE,newName);
            fail();
        }catch (CategoryDoesNotExistException e){

        }catch (Exception e){
            fail();
        }

    }
}
