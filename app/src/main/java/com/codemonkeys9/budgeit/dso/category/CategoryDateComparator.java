package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Comparator;

/*
    This class is used by stubDatabase to allow it to sort the list
    of categories by date
 */
public class CategoryDateComparator implements Comparator<Category> {
    public int compare(Category a, Category b) {
        //return a.getDate().compareTo(b.getDate());
        return 0;
    }
}
