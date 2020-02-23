package com.codemonkeys9.budgeit.dso.categorylist;

import com.codemonkeys9.budgeit.dso.category.Category;

import java.util.List;

public interface CategoryList {
    /*
    Returns the list of categories in no particular order
     */
    List<Category> getList();

    int size();
}
