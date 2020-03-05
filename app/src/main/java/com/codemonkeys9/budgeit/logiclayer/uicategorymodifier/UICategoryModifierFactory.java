package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;

public class UICategoryModifierFactory
{
    public static UICategoryModifier createUICategoryModifier()
    {
        Database db = DatabaseHolder.getDatabase();
        return new CategoryModifier(db);
    }
}
