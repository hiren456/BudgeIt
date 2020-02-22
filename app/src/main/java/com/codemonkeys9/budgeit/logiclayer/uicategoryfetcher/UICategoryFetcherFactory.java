package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;

public class UICategoryFetcherFactory {
    public static UICategoryFetcher createUICategoryFetcher(){
        Database db = DatabaseHolder.getDatabase();
        return new CategoryFetcher(db);
    }
}
