package com.codemonkeys9.budgeit.dso.categorylist;

import com.codemonkeys9.budgeit.dso.category.Category;

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
    public int getReverseChronoIndexOfCategoryWithID(int catID) {
        List<Category> list = this.getReverseChrono();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getID() == catID) {
                return i;
            }
        }

        // If we get to this point, `catID` is not in the list
        throw new IllegalArgumentException("Category with ID " + catID + " is not in the list");
    }

    @Override
    public int size() {
        return this.ll.size();
    }

    public Category get(int i){
        return ll.get(i);
    }
    public Category getInReverseChrono(int i){ return this.getReverseChrono().get(i);}
}
