package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SavingCategoryTest {
    @Test
    public void getNameTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat = SavingsCategoryFactory.createSavingsCategory(name,goal,id);

        assertTrue(name.equals(cat.getName()));
    }

    @Test
    public void getGoalTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat = SavingsCategoryFactory.createSavingsCategory(name,goal,id);

        assertTrue(goal.equals(cat.getGoal()));
    }

    @Test
    public void getidTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat = SavingsCategoryFactory.createSavingsCategory(name,goal,id);

        assertEquals(42,cat.getID());
    }

    @Test
    public void equalsTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat1 = SavingsCategoryFactory.createSavingsCategory(name,goal,id);
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name,goal,id);

        assertTrue(cat1.equals(cat2));
        assertTrue(cat2.equals(cat1));
    }

    @Test
    public void notEqualsTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat1 = SavingsCategoryFactory.createSavingsCategory(name,goal,id);
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name,goal,id-1);

        assertFalse(cat1.equals(cat2));
        assertFalse(cat2.equals(cat1));
    }

    @Test
    public void notEqualsWithSavingsCategoryTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        int id = 42;
        Category cat1 = BudgetCategoryFactory.createBudgetCategory(name,goal,id);
        Category cat2 = SavingsCategoryFactory.createSavingsCategory(name,goal,id);

        assertFalse(cat2.equals(cat1));
    }

    @Test
    public void modifyTest(){
        Details name = DetailsFactory.fromString("Food");
        Amount goal = AmountFactory.fromInt(20000);
        Details newName = DetailsFactory.fromString("More Food");
        Amount newGoal = AmountFactory.fromInt(30000);
        int id = 42;

        Category cat = SavingsCategoryFactory.createSavingsCategory(name,goal,id);
        Category newCat = cat.modifyCategory(newName,newGoal);

        assertTrue(name.equals(cat.getName()));
        assertTrue(goal.equals(cat.getGoal()));
        assertEquals(42 , cat.getID());

        assertTrue(newName.equals(newCat.getName()));
        assertTrue(newGoal.equals(newCat.getGoal()));
        assertEquals(42 , cat.getID());
        assertTrue(newCat instanceof SavingsCategory);
    }
}
