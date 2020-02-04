package com.codemonkeys9.budgeit.Entry;

import java.util.Date;

public interface Entry {

    // getters
    int getAmount();
    int getEntryID();
    //int getCatID();
    String getDetails();
    Date getDate();

    // A method that returns the date as a display friendly string
    String getDisplayDate();

    // takes values and returns an entry
    // with those updated updated values
    Entry modifyEntry(int amount,String details, Date date);

    boolean equals(Entry other);
}
