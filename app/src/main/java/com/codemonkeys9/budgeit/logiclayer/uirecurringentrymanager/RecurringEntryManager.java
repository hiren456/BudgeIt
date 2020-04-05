package com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.dso.entry.RecurringIncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchaseFactory;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class RecurringEntryManager implements UIRecurringEntryManager {
    IDManager idManager;
    Database db;
    EntryCreator entryCreator;
    DateSource dateSource;

    public RecurringEntryManager(IDManager idManager, Database db, DateSource dateSource){
        this.idManager = idManager;
        this.db = db;
        this.entryCreator = EntryCreatorFactory.createEntryCreator(idManager);
        this.dateSource = dateSource;
    }

    @Override
    public int createRecurringEntry(int entryId, RecurrencePeriod recurrencePeriod) {
        BaseEntry entry = getEntry(entryId);

        int recurringEntryId = this.idManager.getNewID("Entry");

        RecurringEntry recurringEntry = null;

        if(entry instanceof Purchase){
            recurringEntry = RecurringPurchaseFactory
                    .createPurchase(entry.getAmount(),recurringEntryId,entry.getDetails(),
                            entry.getDate(),entry.getCatID(),recurrencePeriod,((Purchase) entry).flagged());
        }

        if(entry instanceof Income){
            recurringEntry = RecurringIncomeFactory
                    .createRecurringIncome(entry.getAmount(),recurringEntryId,entry.getDetails(),
                            entry.getDate(),entry.getCatID(),recurrencePeriod);
        }

        db.insertRecurringEntry(recurringEntry);

        // Add all recurrences after up until today
        Date recurrence = getNextRecurrence(recurringEntry.getDate(), recurringEntry.getRecurrencePeriod());
        Date now = dateSource.now();
        while(recurrence.compareTo(now) <= 0) {
            addRecurrence(recurringEntry, recurrence);
            recurrence = getNextRecurrence(recurrence, recurringEntry.getRecurrencePeriod());
        }

        return recurringEntryId;
    }

    private BaseEntry getEntry(int entryId){
        BaseEntry entry = this.db.selectDefaultEntryByID(entryId);

        if(entry == null){
            throw new EntryDoesNotExistException("Entry with id " + entryId + " does not exist.");
        }

        return entry;
    }

    private Date getNextRecurrence(Date start, RecurrencePeriod period) {
        int year = start.getYear() + period.getYears();
        int month = start.getMonth() + period.getMonths();
        int day = start.getDay() + period.getDays() + period.getWeeks() * 7;

        // Correct the month
        while (month > 12) {
            year += 1;
            month -= 12;
        }

        // The day doesn't matter in this context; we just need to know what the length
        // of the month is.
        Date recurrence = DateFactory.fromInts(year, month, 1);
        int lengthOfMonth = recurrence.getLengthOfMonth();

        // Handwavy heuristic alert: if the user entered a non-zero number of days and/or
        // weeks in the recurrence period, and if the calculated date goes past the end of
        // the month, we should respect the user's wishes and roll over to the next (as many
        // times as necessary).
        //
        // On the other hand, if they entered 0 in both the days and weeks fields, we should
        // clamp the date to the last of the month.
        // Example: recurrence period 1 month, start date January 31st
        //          first recurrence date: February 28th (or 29th on a leap year)
        if (period.getDays() + period.getWeeks() > 0) {
            while (day > lengthOfMonth) {
                month += 1;
                day -= lengthOfMonth;
                if (month > 12) {
                    year += 1;
                    month -= 12;
                }

                // See above regarding the day argument
                recurrence = DateFactory.fromInts(year, month, 1);
                lengthOfMonth = recurrence.getLengthOfMonth();
            }
        } else {
            day = Math.min(lengthOfMonth, day);
        }
        return DateFactory.fromInts(year, month, day);
    }

    private void addRecurrence(RecurringEntry entry, Date recurrence) {
        if (entry instanceof RecurringPurchase) {
            RecurringPurchase purchase = (RecurringPurchase) entry;
            this.entryCreator.createPurchase(entry.getAmount(), entry.getDetails(), recurrence.clone(), purchase.flagged());
        } else {
            this.entryCreator.createIncome(entry.getAmount(), entry.getDetails(), recurrence.clone());
        }
    }

    @Override
    public void checkAllRecurringEntrys() {
        Date lastChecked = db.getDateLastChecked("Recurring Entry");
        Date now = dateSource.now();
        if(lastChecked.equals(now)) return;

        List<RecurringEntry> entries = db.getAllRecurringEntries();
        for(RecurringEntry entry: entries) {
            Date recurrence = entry.getDate();

            // Iterate over all recurrences between the original date and now (inclusive)
            while(recurrence.compareTo(now) <= 0) {
                // Create the entry
                if (lastChecked.compareTo(recurrence) < 0) addRecurrence(entry, recurrence);

                // Get the next recurrence
                recurrence = getNextRecurrence(recurrence, entry.getRecurrencePeriod());
            }
        }
        this.db.updateDateLastChecked("Recurring Entry",now);
    }

    @Override
    public void scheduleCheckAllRecurringEntriesEveryDay(final NewRecurringEntriesDelegate delegate) {
        Timer timer = new Timer("check recurring entries", false);
        timer.schedule(
            new TimerTask() {
                public void run() {
                    checkAllRecurringEntrys();
                    delegate.receivedNewEntries();
                    scheduleCheckAllRecurringEntriesEveryDay(delegate);
                }
            },
            dateSource.tomorrowAtMidnight()
        );
    }
}
