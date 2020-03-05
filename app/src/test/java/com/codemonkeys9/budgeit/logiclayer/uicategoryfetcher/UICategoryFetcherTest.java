package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UICategoryFetcherTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
    }

    @Test
    public void fetchAllCategoryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();


        String goal = "200.00";
        String name = "Food";
        creator.createBudgetCategory(goal,name);

        CategoryList list = fetcher.fetchAllCategories();
        Category cat = list.getChrono().get(0);


        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat instanceof BudgetCategory);
    }

    @Test
    public void fetchAllBudgetCategoriesTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();


        String goal = "200.00";
        String name = "Food";
        creator.createBudgetCategory(goal,name);

        String goal2 = "300.00";
        String name2 = "Phone";
        creator.createSavingsCategory(goal2,name2);

        CategoryList list = fetcher.fetchAllBudgetCategories();
        Category cat = list.getChrono().get(0);

        assertEquals(1,list.size());

        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat instanceof BudgetCategory);
    }

    @Test
    public void fetchAllSavingsCategoriesTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.createUICategoryCreator();
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();


        String goal = "200.00";
        String name = "Food";
        creator.createBudgetCategory(goal,name);

        String goal2 = "300.00";
        String name2 = "Phone";
        creator.createSavingsCategory(goal2,name2);

        CategoryList list = fetcher.fetchAllSavingsCategories();
        Category cat = list.getChrono().get(0);

        assertEquals(1,list.size());

        assertTrue(cat.getName().getValue().equals(name2));
        assertTrue(cat.getGoal().getDisplay().equals(goal2));
        assertTrue(cat instanceof SavingsCategory);
    }

    @Test
    public void fetchAllCategoryWithNoneTest(){
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();

        CategoryList list = fetcher.fetchAllCategories();
        assertEquals(0,list.size());
    }
}
