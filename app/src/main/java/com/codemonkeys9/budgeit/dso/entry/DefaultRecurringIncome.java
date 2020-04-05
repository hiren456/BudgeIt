package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

class DefaultRecurringIncome extends DefaultRecurringEntry implements RecurringIncome {
    DefaultRecurringIncome(Amount amount, int recurringEntryID, Details details, Date date, int catID, RecurrencePeriod period) {
        super(amount, recurringEntryID, details, date,catID, period);
    }

    @Override
    public int getEntryID() {
        return 0;
    }

    @Override
    public DefaultRecurringIncome modifyEntry(Amount amount, Details details, Date date) {
        return (DefaultRecurringIncome)super.modifyEntry(amount, details, date);
    }

    @Override
    public DefaultRecurringIncome changeCategory(int catID) {
        return (DefaultRecurringIncome)super.changeCategory(catID);
    }

    @Override
    public DefaultRecurringIncome clone() {
        return new DefaultRecurringIncome(amount.clone(), recurringEntryID, details.clone(), date.clone(), catID, period.clone());
    }
}
