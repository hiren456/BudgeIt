package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public class RecurringPurchaseFactory {
    public static RecurringPurchase createPurchase(Amount amount, int entryID, Details details, Date date, int catID, RecurrencePeriod period, boolean flag){
        return new DefaultRecurringPurchase(amount, entryID, details, date, catID, period, flag);
    }
    public static RecurringPurchase createPurchase(Amount amount, int entryID, Details details, Date date, RecurrencePeriod period, int catID){
        return new DefaultRecurringPurchase(amount, entryID, details, date, catID, period, false);
    }
}
