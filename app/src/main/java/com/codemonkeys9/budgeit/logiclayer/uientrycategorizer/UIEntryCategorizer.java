package com.codemonkeys9.budgeit.logiclayer.uientrycategorizer;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;

public interface UIEntryCategorizer {
    /*
    takes an entry and category object, or their id's,
    and adds the entry to that category.
    if the entry has not been created an EntryDoesNotExistException will be thrown
    if the category has not been created a CategoryDoesNotException will be thrown
     */
    void categorizeEntry(Entry entry, Category category)
            throws EntryDoesNotExistException, CategoryDoesNotExistException;
    void categorizeEntry(int entryID, int categoryID)
            throws EntryDoesNotExistException, CategoryDoesNotExistException;
}
