package com.codemonkeys9.budgeit.database;

public class DatabaseFactory {
    public static Database createDatabase(int initialEntryID,int initialCategoryID){
        return new StubDatabase(initialEntryID,initialCategoryID);
    }
}
