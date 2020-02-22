package com.codemonkeys9.budgeit.logiclayer.entrycategorizer;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;

public class UIEntryCategorizerFactory {
    public static UIEntryCategorizer createUIEntryCategorizer(){
        Database db = DatabaseHolder.getDatabase();
        return new EntryCategorizer(db);
    }
}
