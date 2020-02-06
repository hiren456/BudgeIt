package com.codemonkeys9.budgeit.database;

public class DatabaseFactory {
    public Database createDatabase(int initialEntryID){
        return new StubDatabase(initialEntryID);
    }
}
