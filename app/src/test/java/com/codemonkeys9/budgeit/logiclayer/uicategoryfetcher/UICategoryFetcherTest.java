package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UICategoryFetcherTest {
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
    public void fetchAllCategoryTest(){
        UICategoryCreator creator = UICategoryCreatorFactory.creatorUICategoryCreator();
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();


        String goal = "200.00";
        String name = "Food";
        creator.createBudgetCategory(goal,name);

        CategoryList list = fetcher.getAllCategories();
        Category cat = list.getChrono().get(0);


        assertTrue(cat.getName().getValue().equals(name));
        assertTrue(cat.getGoal().getDisplay().equals(goal));
        assertTrue(cat instanceof BudgetCategory);
    }

    @Test
    public void fetchAllCategoryWithNoneTest(){
        UICategoryFetcher fetcher = UICategoryFetcherFactory.createUICategoryFetcher();

        CategoryList list = fetcher.getAllCategories();
        assertEquals(0,list.size());
    }
}
