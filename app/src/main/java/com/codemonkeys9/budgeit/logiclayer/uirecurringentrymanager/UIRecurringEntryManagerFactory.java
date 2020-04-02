package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class UIRecurringEntryManagerFactory {
    public static UIRecurringEntryManager createUIReccuringEntryManager(){
        IDManager idManager = IDManagerFactory.createIDManager();
        return new RecurringEntryManager(idManager);
    }
    public static UIRecurringEntryManager createUIReccuringEntryManager(IDManager idManager){
        return new RecurringEntryManager(idManager);
    }
}
