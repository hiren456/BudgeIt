package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

class RecurringEntryManager implements UIRecurringEntryManager {
    IDManager idManager;
    Database db;
    DateSource dateSource = new IRLDateSource();

    public RecurringEntryManager(IDManager idManager){
        this.idManager = idManager;
        this.db = DatabaseHolder.getDatabase();
    }

    @Override
    public int createRecurringEntry(int entryId, RecurrencePeriod recurrencePeriod) {

        return 0;
    }

    @Override
    public void checkAllRecurringEntrys() {

    }
}
