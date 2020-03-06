package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class DatabaseHolder {
    private static Database db;

    public static synchronized void init() {
        if(db == null) {
            IDManager idManager = IDManagerFactory.createIDManager();
            db = DatabaseFactory.createDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category"));
        }
    }

    public static synchronized Database getDatabase() {
        return db;
    }

    //only for tests
    public static synchronized void initTestable(Database database) {
        db = database;
    }

}

