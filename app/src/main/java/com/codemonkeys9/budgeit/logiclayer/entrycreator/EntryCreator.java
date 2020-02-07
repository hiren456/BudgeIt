package com.codemonkeys9.budgeit.logiclayer.entrycreator;

public interface EntryCreator {

    // Creates and stores an entry
    void createEntry(String amount, String details,String date);
}