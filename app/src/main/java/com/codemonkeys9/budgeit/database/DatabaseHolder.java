package com.codemonkeys9.budgeit.database;

public class DatabaseHolder {
    private static Database db;
    public static synchronized Database getDatabase() {
            return db;
    }

    public static synchronized void init() {
        if(db == null) {
            db = DatabaseFactory.createDatabase(1,1);
        }
    }
}

