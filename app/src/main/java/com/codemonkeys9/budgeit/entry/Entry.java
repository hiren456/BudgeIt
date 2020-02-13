package com.codemonkeys9.budgeit.entry;

import java.util.Date;

// add catID
// add
public interface Entry {

    // getters
    int getAmount();
    int getEntryID();
    //int getCatID();
    String getDetails();
    Date getDate();

    // A method that returns the date as a display friendly string
    String getDisplayDate();

    // A method that returns the amount as a display friendly string
    String getDisplayAmount();

    // Add flag/catID

    // takes values and returns an entry
    // with those updated updated values
    Entry modifyEntry(int amount,String details, Date date);

    boolean equals(Entry other);
}
