package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultRecurringEntry extends DefaultBaseEntry implements RecurringEntry {
    int recurringEntryID;
    RecurrencePeriod period;

    DefaultRecurringEntry(Amount amount, int recurringEntryID, Details details, Date date, int catID, RecurrencePeriod period) {
        super(amount, details, date, catID);
        this.recurringEntryID = recurringEntryID;
        this.period = period;
    }

    @Override
    public DefaultRecurringEntry modifyEntry(Amount amount, Details details, Date date) {
        return (DefaultRecurringEntry) super.modifyEntry(amount, details, date);
    }

    @Override
    public abstract DefaultRecurringEntry clone();

    @Override
    public DefaultRecurringEntry changeCategory(int catID) {
        return (DefaultRecurringEntry) super.changeCategory(catID);
    }

    @Override
    public DefaultRecurringEntry changeRecurrencePeriod(RecurrencePeriod period) {
        DefaultRecurringEntry entry = this.clone();
        entry.period = period.clone();
        return entry.clone();
    }

    @Override
    public int getEntryID() {
        return this.recurringEntryID;
    }

    @Override
    public RecurrencePeriod getRecurrencePeriod() { return period; }

    @Override
    public boolean equals(RecurringEntry other) {
        return this.getEntryID() == other.getEntryID() &&
               this.getRecurrencePeriod() == other.getRecurrencePeriod() &&
               super.equals(other);
    }
}
