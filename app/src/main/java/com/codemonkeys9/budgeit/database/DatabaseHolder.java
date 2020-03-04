package com.codemonkeys9.budgeit.database;

import android.content.Context;

public class DatabaseHolder {
    private static Database db;

    public static synchronized void init() {
        if(db == null) {
            db = DatabaseFactory.createDatabase(1,1);
        }
    }

    public static synchronized Database getDatabase() {
        return db;
    }

    //only for tests
    public static synchronized void initTestable(Context context) {
        if(db == null) {
            db = DatabaseFactory.createTestableDatabase(context,1,1);
        }
    }

}

