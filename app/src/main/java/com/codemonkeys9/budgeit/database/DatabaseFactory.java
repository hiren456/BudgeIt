package com.codemonkeys9.budgeit.database;

public class DatabaseFactory {
    public static Database createDatabase(int initialEntryID){
        return new StubDatabase(initialEntryID);
    }
}
