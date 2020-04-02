package com.codemonkeys9.budgeit.database;

import android.content.Context;

import com.codemonkeys9.budgeit.application.BudApplication;

public class DatabaseFactory {
    public static Database createDatabase(int initialEntryID,int initialCategoryID){
        return new EntryCategorySQLitePersistence(BudApplication.getContext(),initialEntryID,initialCategoryID);
    }

    public static Database createStubDatabase(int initialEntryID,int initialCategoryID){
        return new StubDatabase(initialEntryID,initialCategoryID);
    }


    public static Database createTestableDatabase(Context context, int initialEntryID, int initialCategoryID){
        return new EntryCategorySQLitePersistence(context,initialEntryID,initialCategoryID);
    }
}
