package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;

public interface UICategoryFetcher {
    /*
    ... gets all the categories that have been created
     */
    CategoryList fetchAllCategories();
}
