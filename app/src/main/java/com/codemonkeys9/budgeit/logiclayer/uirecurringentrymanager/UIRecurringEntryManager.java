package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;

public interface UIRecurringEntryManager {
    int createRecurringEntry(int entryId, RecurrencePeriod recurrencePeriod)
            throws EntryDoesNotExistException;
    void checkAllRecurringEntrys();
    void scheduleCheckAllRecurringEntriesEveryDay(NewRecurringEntriesDelegate delegate);
}
