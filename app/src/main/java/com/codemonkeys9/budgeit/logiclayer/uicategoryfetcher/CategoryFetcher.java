package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryListFactory;

import java.util.List;


class CategoryFetcher implements UICategoryFetcher {
    Database db;

    CategoryFetcher(Database db){
        this.db = db;
    }

    @Override
    public CategoryList getAllCategories() {
        List<Category> list = db.getAllCategories();
        CategoryList ret = CategoryListFactory.fromList(list);
        return ret;
    }
}
