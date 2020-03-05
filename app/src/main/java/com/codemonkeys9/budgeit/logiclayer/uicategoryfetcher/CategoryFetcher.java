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
    public CategoryList fetchAllCategories() {
        CategoryList ret = CategoryListFactory.fromChrono(getAll());
        return ret;
    }

    @Override
    public CategoryList fetchAllBudgetCategories() {
        List<Category> list = getAll();
        Filterer.getBudget(list);
        CategoryList ret = CategoryListFactory.fromChrono(list);
        return ret;
    }

    @Override
    public CategoryList fetchAllSavingsCategories() {
        List<Category> list = getAll();
        Filterer.getSavings(list);
        CategoryList ret = CategoryListFactory.fromChrono(list);
        return ret;
    }

    private List<Category> getAll(){
        return db.getAllCategories();
    }
}
