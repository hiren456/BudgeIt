package com.codemonkeys9.budgeit.dso.categorylist;

import com.codemonkeys9.budgeit.dso.category.Category;

import java.util.List;

public class CategoryListFactory {
    public static CategoryList fromList(List<Category> list){
        return new LinkedCategoryList(list);
    }
}
