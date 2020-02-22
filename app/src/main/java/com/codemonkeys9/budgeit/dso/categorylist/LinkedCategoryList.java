package com.codemonkeys9.budgeit.dso.categorylist;

import com.codemonkeys9.budgeit.dso.category.Category;

import java.util.LinkedList;
import java.util.List;

class LinkedCategoryList implements CategoryList {
    LinkedList<Category> ll;

    LinkedCategoryList(List<Category> list){
        this.ll = new LinkedList<Category>(list);
    }

    @Override
    public List<Category> getList() {
        List<Category> cpy = new LinkedList<Category>(this.ll);
        return cpy;
    }

    @Override
    public int size() {
        return this.ll.size();
    }
}
