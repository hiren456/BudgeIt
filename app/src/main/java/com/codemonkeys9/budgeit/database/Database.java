package com.codemonkeys9.budgeit.database;

import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.entry.Entry;

public interface Database {

    //Inserts an entry into the database
    void insertEntry(Entry entry);

    //Update an entry in the database
    void updateEntry(Entry entry);

    //return an entry by ID
    //if not found returns null
    Entry selectByID(int ID);

    //returns the list of entries from Date startDate till Date endDate
    //returns empty list if the are no entries
    List<Entry> selectByDate(Date startDate, Date endDate);

    //TODO
    //List<Entry> selectByCategory(int catID);

    //returns true if an entry deleted successfully
    boolean deleteEntry(int ID);

    // returns current entry id counter
    int getIDCounter();

    // updates entry id counter
    void updateIDCounter(int newCounter);

}
