package com.codemonkeys9.budgeit.dso.categorylist;

import com.codemonkeys9.budgeit.dso.category.Category;

import java.util.Iterator;
import java.util.List;

public interface CategoryList {
    /*
    returns the list of categories in chronological order
     */
    List<Category> getChrono();
    Iterator<Category> getChronoIter();

    /*
    returns the list of categories in reverse chronological order
     */
    List<Category> getReverseChrono();
    Iterator<Category> getReverseChronoIter();

    /*
    Returns index in a reverse chronologically-ordered list of the category with the desired ID
     */
    int getReverseChronoIndexOfCategoryWithID(int catID);

    /*
    returns the size of the list
     */
    int size();

    Category get(int i);
    Category getInReverseChrono(int i);
}
