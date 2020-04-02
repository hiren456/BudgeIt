package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

public class UIRecurringEntryManagerFactory {
    public static UIRecurringEntryManager createUIReccuringEntryManager(){
        return new RecurringEntryManager();
    }
}
