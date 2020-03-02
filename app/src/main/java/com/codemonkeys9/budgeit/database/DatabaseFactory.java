package com.codemonkeys9.budgeit.database;

public class DatabaseFactory {
    public static Database createDatabase(int initialEntryID,int initialCategoryID){
        return new StubDatabase(initialEntryID,initialCategoryID);
    }

//    public static Database createDatabase(int initialEntryID, int initialCategoryID, Context context){
//        if(context == null){
//            context = BudApplication.getContext();
//        }
//        return new StubDatabase(initialEntryID,initialCategoryID);
//    }
}
