package com.codemonkeys9.budgeit.entry;

import java.time.LocalDate;

public interface Entry {

    // getters
    int getAmount();
    int getEntryID();
    //int getCatID();
    String getDetails();
    LocalDate getDate();
    // Add flag/catID

    // A method that returns the date as a display friendly string
    String getDisplayDate();

    // A method that returns the amount as a display friendly string
    String getDisplayAmount();


    // add the ability to flag/unflag
    // add the ability to update catID
    // takes values and returns an entry
    // with those updated updated values
    Entry modifyEntry(int amount,String details, LocalDate date);

    boolean equals(Entry other);
}
