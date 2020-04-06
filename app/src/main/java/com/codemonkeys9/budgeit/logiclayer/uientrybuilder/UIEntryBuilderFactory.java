package com.codemonkeys9.budgeit.logiclayer.uientrybuilder;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;

public class UIEntryBuilderFactory {
    public static UIEntryBuilder createUIEntryBuilder(){
        Database db = DatabaseHolder.getDatabase();
        return new EntryBuilder(db);
    }
}
