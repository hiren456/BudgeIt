package com.codemonkeys9.budgeit.LogicLayer.EntryCreator;

public interface EntryCreator {

    // Creates and stores an entry
    void createEntry(String amount, String details,String date);
}