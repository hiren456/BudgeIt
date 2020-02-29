package com.codemonkeys9.budgeit.database;

import java.util.List;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.Entry;

public interface Database {

    /*
    Inserts an Entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    void insertEntry(Entry entry);

    /*
    Update the entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    boolean updateEntry(Entry entry);

    /*
    return an entry by ID
    if not found returns null
     */
    Entry selectByID(int ID);

    /*
    return a list of all entries sorted by date
    or an empty list if db is empty
     */
    List<Entry> getAllEntries();

    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    List<Entry> selectByDate(DateInterval dateInterval);

    /*
    return a list of entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    List<Entry> getEntriesByCategoryID(int ID);

    /*
    delete an entry and return true if the entry deleted successfully,
    otherwise return false
     */
    boolean deleteEntry(int ID);

    /*
    Inserts a Category into the database.
    If the category with the same ID is in the db throws runtime exception
     */
    void insertCategory(Category category);

    /*
    Update the Category
    return true if the category is found in the database and then updated, otherwise return false
     */
    boolean updateCategory(Category category);

    /*
    return a category by ID
    if not found returns null
     */
    Category selectCategoryByID(int ID);

    /*
    returns a list of all Categories sorted by date
    or an empty list if db is empty
     */
    List<Category> getAllCategories();

    /*
    delete a category and return true if the category was deleted successfully,
    otherwise return false
     */
    boolean deleteCategory(int ID);

    /*
     returns current entry id counter
     Possible idNames are "Entry" and "Category"
     */
    int getIDCounter(String idName);

    /*
     updates entry id counter
     */
    void updateIDCounter(String idName, int newCounter);
}
