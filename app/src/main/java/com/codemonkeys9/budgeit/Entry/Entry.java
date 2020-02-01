package com.codemonkeys9.budgeit.Entry;

import java.util.Date;

public interface Entry {

<<<<<<< HEAD
=======
    // not allowed in interface
    //com.codemonkeys9.budgeit.Entry.com.codemonkeys9.budgeit.Entry(int amount,int entryID,int catID, String details, Date date);

>>>>>>> master
    // getters
    int getAmount();
    int getEntryID();
    //int getCatID();
    String getDetails();
    Date getDate();

    // takes values and returns an entry
    // with those updated updated values
    Entry modifyEntry(int amount,String details, Date date);
}
