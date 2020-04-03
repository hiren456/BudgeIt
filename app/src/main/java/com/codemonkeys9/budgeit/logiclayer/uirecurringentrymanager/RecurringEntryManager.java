package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
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
                RecurrencePeriod period = entry.getRecurrencePeriod();
                int year = recurrence.getYear() + period.getYears();
                int month = recurrence.getMonth() + period.getMonths();
                int day = recurrence.getDay() + period.getDays();

                // Correct the month
                while(month > 12) {
                    year += 1;
                    month -= 12;
                }

                // The day doesn't matter in this context; we just need to know what the length
                // of the month is.
                recurrence = DateFactory.fromInts(year, month, 0);
                int lengthOfMonth = recurrence.getLengthOfMonth();

                // Handwavy heuristic alert: if the user entered a non-zero number of days in the
                // recurrence period, should the calculated date go past the end of the month, we
                // should respect their wishes and roll over to the next (as many times as necessary).
                //
                // On the other hand, if they left the `days` field as 0, we should clamp the date
                // to the last of the month.
                // Example: recurrence period 1 month, start date January 31st
                //          first recurrence date: February 28th (or 29th on a leap year)
                if(period.getDays() > 0) {
                    while (day > lengthOfMonth) {
                        month += 1;
                        day -= lengthOfMonth;
                        if (month > 12) {
                            year += 1;
                            month -= 12;
                        }

                        recurrence = DateFactory.fromInts(year, month, 0);
                        lengthOfMonth = recurrence.getLengthOfMonth();
                    }
                } else {
                    day = Math.min(lengthOfMonth, day);
                }

                recurrence = DateFactory.fromInts(year, month, day);
            }
        }
    }
}
