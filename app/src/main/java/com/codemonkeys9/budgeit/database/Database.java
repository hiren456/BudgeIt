package com.codemonkeys9.budgeit.database;

import java.util.List;

import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.Entry;

public interface Database {


    // Iteration 2.5 store two ID one for cat and one for entry


    /*
    Inserts an entry into the database
     */
    void insertEntry(Entry entry);

    /*
    Update the entry
    return true if the entry is found in the hashmap and then updated, otherwise return false
     */
    boolean updateEntry(Entry entry);

    /*
    return an entry by ID
    if not found returns null
     */
    Entry selectByID(int ID);

    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    List<Entry> selectByDate(DateInterval dateInterval);

    /*
    delete an entry and return true if the entry deleted successfully,
    otherwise return false
     */
    boolean deleteEntry(int ID);

    /*
     returns current entry id counter
     */
    int getIDCounter();

    /*
     updates entry id counter
     */
    void updateIDCounter(int newCounter);

    // Iteration 2.5 Create all functionality for Category's
    // Iteration 2.5except for select by date
}
