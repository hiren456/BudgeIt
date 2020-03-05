package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class BudgetCategoryTest {
    @Test
    public void getDateLastModifiedTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);

        assertTrue(date.equals(cat.getDateLastModified()));
    }
    @Test
    public void getNameTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);

        assertTrue(name.equals(cat.getName()));
    }

    @Test
    public void getGoalTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);

        assertTrue(goal.equals(cat.getGoal()));
    }

    @Test
    public void getidTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);

        assertEquals(42,cat.getID());
    }

    @Test
    public void equalsTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat1 = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);
        Category cat2 = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);

        assertTrue(cat1.equals(cat2));
        assertTrue(cat2.equals(cat1));
    }

    @Test
    public void notEqualsTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat1 = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);
        Category cat2 = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id-1);

        assertFalse(cat1.equals(cat2));
        assertFalse(cat2.equals(cat1));
    }

    @Test
    public void notEqualsWithSavingsCategoryTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        int id = 42;
        Category cat1 = BudgetCategoryFactory.createBudgetCategory(name,goal, date, id);
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name,goal, date, id);

        assertFalse(cat1.equals(cat2));
    }

    @Test
    public void modifyTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Date date = DateFactory.fromInts(1999,04,23);
        Details newName = DetailsFactory.fromString("More Food");
        Date newDate = DateFactory.fromString("now");
        Amount newGoal = AmountFactory.fromInt(30000);
        int id = 42;

        Category cat = BudgetCategoryFactory.createBudgetCategory(name,goal,date,id);
        Category newCat = cat.modifyCategory(newName,newGoal,newDate);

        assertTrue(name.equals(cat.getName()));
        assertTrue(goal.equals(cat.getGoal()));
        assertTrue(date.equals(cat.getDateLastModified()));
        assertEquals(42 , cat.getID());

        assertTrue(newName.equals(newCat.getName()));
        assertTrue(newGoal.equals(newCat.getGoal()));
        assertTrue(newDate.equals(newCat.getDateLastModified()));
        assertEquals(42 , newCat.getID());
        assertTrue(newCat instanceof BudgetCategory);
    }
}
