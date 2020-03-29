package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface RecurringEntry extends BaseEntry {
    // getters
    int getRecurringEntryID();
    RecurrencePeriod getRecurrencePeriod();

    RecurringEntry modifyEntry(Amount amount, Details details, Date startDate, RecurrencePeriod recurrencePeriod);
    RecurringEntry changeCategory(int catID);

    boolean equals(RecurringEntry other);
}
