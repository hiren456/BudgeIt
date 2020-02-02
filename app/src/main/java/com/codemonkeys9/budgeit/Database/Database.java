package com.codemonkeys9.budgeit.Database;

import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.Entry.Entry;

public interface Database {

    //Inserts an entry into the database
    void insertEntry(Entry entry);

    Entry selectByID(int ID);

    List<Entry> selectByDate(Date startDate, Date endDate);

    //List<Entry> selectByCategory(int catID);

    boolean deleteEntry(int ID);

    // returns current entry id counter
    int getIDCounter();

    // updates entry id counter
    void updateIDCounter(int newCounter);

}
