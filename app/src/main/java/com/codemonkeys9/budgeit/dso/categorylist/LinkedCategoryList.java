package com.codemonkeys9.budgeit.dso.categorylist;

import android.graphics.Matrix;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class LinkedCategoryList implements CategoryList {
    LinkedList<Category> ll;

    LinkedCategoryList(List<Category> list){
        this.ll = new LinkedList<Category>(list);
    }

    @Override
    public List<Category> getChrono() {
        List<Category> copy = new LinkedList<Category>(this.ll);

        return copy;
    }

    @Override
    public Iterator<Category> getChronoIter() {
        return this.getChrono().iterator();
    }

    @Override
    public List<Category> getReverseChrono() {
        List<Category> copy = new LinkedList<Category>(this.ll);

        Collections.reverse(copy);

        return copy;
    }

    @Override
    public Iterator<Category> getReverseChronoIter() {

        return this.getReverseChrono().iterator();
    }

    @Override
    public int size() {
        return this.ll.size();
    }
}
