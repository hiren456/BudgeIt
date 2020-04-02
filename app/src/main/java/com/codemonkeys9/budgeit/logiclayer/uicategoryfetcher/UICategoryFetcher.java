package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;

public interface UICategoryFetcher {
    /*
    ... gets all the categories that have been created
     */
    CategoryList fetchAllCategories();

    /*
    ... gets all the budget categories that have been created
     */
    CategoryList fetchAllBudgetCategories();

    /*
    ... gets all the savings categories that have been created
     */
    CategoryList fetchAllSavingsCategories();

    /*
    ... gets category with ID catID
     */
    Category getCategoryWithID(int catID);
}
