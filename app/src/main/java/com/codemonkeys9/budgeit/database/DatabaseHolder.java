package com.codemonkeys9.budgeit.database;

public class DatabaseHolder {
    private static Database db;
    public static Database getDatabase() {
            return db;
    }

    public static void init() {
        if(db == null) {
            db = DatabaseFactory.createDatabase(1,1);
        }
    }
}

