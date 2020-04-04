package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class UIRecurringEntryManagerFactory {
    public static UIRecurringEntryManager createUIReccuringEntryManager(){
        IDManager idManager = IDManagerFactory.createIDManager();
        Database db = DatabaseHolder.getDatabase();
        DateSource dateSource = new IRLDateSource();
        return new RecurringEntryManager(idManager, db, dateSource);
    }
    public static UIRecurringEntryManager createUIReccuringEntryManager(IDManager idManager, Database db, DateSource dateSource){
        return new RecurringEntryManager(idManager, db, dateSource);
    }
}
