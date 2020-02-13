package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import java.util.Date;

public interface EntryCreator {

    // Creates and stores an entry
    void createEntry(int amount, String details, Date date);
}