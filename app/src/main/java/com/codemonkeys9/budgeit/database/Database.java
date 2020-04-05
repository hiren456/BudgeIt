package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;

import java.util.List;

public interface Database {

    /*
    Get the date that the logic layer
    last checked type
     */
    Date getDateLastChecked(String type);

    /*
    Update the date last checked
     */
    boolean updateDateLastChecked(String type, Date date);

    /*
    Inserts a default entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    void insertDefaultEntry(Entry entry);

    /*
    Update the default entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    boolean updateDefaultEntry(BaseEntry entry);

    /*
    return a default entry by ID
    if not found returns null
     */
    BaseEntry selectDefaultEntryByID(int ID);

    /*
    return a list of all default entries sorted by date
    or an empty list if db is empty
     */
    List<BaseEntry> getAllDefaultEntries();

    /*
    returns the list of default entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    List<BaseEntry> selectDefaultEntriesByDate(DateInterval dateInterval);


    /*
    returns the list of default entries from that fall within the dateInterval
    and by category specified category ID, returns empty list if the are no entries
     */
    List<BaseEntry> selectDefaultEntriesByDateAndCategoryID(DateInterval dateInterval, int catID);

    /*
    return a list of default entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    List<BaseEntry> getDefaultEntriesByCategoryID(int ID);

    /*
    delete a default entry and return true if the entry deleted successfully,
    otherwise return false
     */
    boolean deleteDefaultEntry(int ID);

    /*
    Inserts a recurring entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    void insertRecurringEntry(RecurringEntry entry);

    /*
    Update the recurring entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    boolean updateRecurringEntry(RecurringEntry entry);

    /*
    return a recurring entry by ID
    if not found returns null
     */
    RecurringEntry selectRecurringEntryByID(int ID);

    /*
    return a list of all recurring entries sorted by date
    or an empty list if db is empty
     */
    List<RecurringEntry> getAllRecurringEntries();

    /*
    returns the list of recurring entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    List<RecurringEntry> selectRecurringEntriesByDate(DateInterval dateInterval);

    /*
    returns the list of recurring entries from that fall within the dateInterval
    and by category specified category ID, returns empty list if the are no entries
     */
    List<RecurringEntry> selectRecurringEntriesByDateAndCategoryID(DateInterval dateInterval, int catID);

    /*
    return a list of recurring entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    List<RecurringEntry> getRecurringEntriesByCategoryID(int ID);

    /*
    delete a recurring entry and return true if the entry deleted successfully,
    otherwise return false
     */
    boolean deleteRecurringEntry(int ID);

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

    /*
    closes the db
     */
    void close();

    /*
    deletes everything from tables in the db
     */
    void clean();
}
