package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

class DefaultRecurringPurchase extends DefaultRecurringEntry implements RecurringPurchase {
    boolean flag;

    DefaultRecurringPurchase(Amount amount, int recurringEntryID, Details details, Date date, int catID, RecurrencePeriod period, boolean flag) {
        super(amount, recurringEntryID, details, date, catID, period);
        this.flag = flag;
    }

    @Override
    public boolean flagged() {
        return this.flag;
    }

    @Override
    public DefaultRecurringPurchase flag() {
        DefaultRecurringPurchase purchase = this.clone();
        purchase.flag = true;
        return purchase;
    }

    @Override
    public DefaultRecurringPurchase unflag() {
        DefaultRecurringPurchase purchase = this.clone();
        purchase.flag = false;
        return purchase;
    }

    @Override
    public DefaultRecurringPurchase clone() {
        return new DefaultRecurringPurchase(amount.clone(), recurringEntryID, details.clone(), date.clone(), catID, period.clone(), flag);
    }

    @Override
    public boolean equals(RecurringEntry other) {
        if(!super.equals(other)) return false;

        if(other instanceof RecurringPurchase) {
            return flagged() == ((RecurringPurchase) other).flagged();
        } else {
            throw new RuntimeException("If `other` is not an instance of RecurringPurchase, super.equals() should return false");
        }
    }
}
