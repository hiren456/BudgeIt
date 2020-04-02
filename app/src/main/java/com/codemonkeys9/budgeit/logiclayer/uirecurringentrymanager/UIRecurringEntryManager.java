package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;

public interface UIRecurringEntryManager {
    public void createRecurringEntry(int entryId, RecurrencePeriod recurrencePeriod);
    public void checkAllRecurringEntrys();
}
