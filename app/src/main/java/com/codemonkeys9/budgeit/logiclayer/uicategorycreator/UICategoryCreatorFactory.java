package com.codemonkeys9.budgeit.logiclayer.uicategorycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class UICategoryCreatorFactory {
    public static UICategoryCreator createUICategoryCreator(){
        Database db = DatabaseHolder.getDatabase();
        IDManager idManager = IDManagerFactory.createIDManager();
        return new CategoryCreator(db,idManager);
    }
}
