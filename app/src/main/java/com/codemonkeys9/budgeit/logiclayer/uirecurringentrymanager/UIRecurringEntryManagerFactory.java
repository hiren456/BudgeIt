package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

public class UIRecurringEntryManagerFactory {
    public static UIRecurringEntryManager createUIReccuringEntryManager(){
        IDManager idManager = IDManagerFactory.createIDManager();
        DateSource dateSource = new IRLDateSource();
        return new RecurringEntryManager(idManager,dateSource);
    }
    public static UIRecurringEntryManager createUIReccuringEntryManager(IDManager idManager,DateSource dateSource){
        return new RecurringEntryManager(idManager,dateSource);
    }
}
