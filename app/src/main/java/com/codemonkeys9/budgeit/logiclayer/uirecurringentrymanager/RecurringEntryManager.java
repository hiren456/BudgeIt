package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncome;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

import java.util.List;

class RecurringEntryManager implements UIRecurringEntryManager {
    IDManager idManager;
    Database db;
    EntryCreator entryCreator;
    DateSource dateSource = new IRLDateSource();

    public RecurringEntryManager(IDManager idManager){
        this.idManager = idManager;
        this.db = DatabaseHolder.getDatabase();
        this.entryCreator = EntryCreatorFactory.createEntryCreator(idManager);
    }

    @Override
    public int createRecurringEntry(int entryId, RecurrencePeriod recurrencePeriod) {

        return 0;
    }

    @Override
    public void checkAllRecurringEntrys() {
        Date lastChecked = db.getDateLastChecked("Recurring Entry");
        Date now = dateSource.now();
        if(lastChecked.equals(now)) return;

        List<RecurringEntry> entries = db.getAllRecurringEntries();
        for(RecurringEntry entry: entries) {
            Date recurrence = entry.getDate().clone();

            // Iterate over all recurrences between the original date and now (inclusive)
            while(recurrence.compareTo(now) <= 0) {
                // Create the entry
                if(lastChecked.compareTo(recurrence) < 0) {
                    if (entry instanceof RecurringPurchase) {
                        RecurringPurchase purchase = (RecurringPurchase) entry;
                        this.entryCreator.createPurchase(entry.getAmount(), entry.getDetails(), recurrence.clone(), purchase.flagged());
                    } else {
                        this.entryCreator.createIncome(entry.getAmount(), entry.getDetails(), recurrence.clone());
                    }
                }

                // Get the next recurrence
                // TODO: actually do that
            }
        }
    }
}
