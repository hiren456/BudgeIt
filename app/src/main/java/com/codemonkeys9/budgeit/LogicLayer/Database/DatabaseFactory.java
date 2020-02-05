package com.codemonkeys9.budgeit.LogicLayer.Database;

public class DatabaseFactory {
    public Database createDatabase(int initialEntryID){
        return new StubDatabase(initialEntryID);
    }
}
