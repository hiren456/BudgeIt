package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public class RecurringIncomeFactory {
    public static RecurringIncome createRecurringIncome(Amount amount, int recurringEntryID, Details details, Date date, int catID, RecurrencePeriod period){
        return new DefaultRecurringIncome(amount, recurringEntryID, details, date, catID, period);
    }
}
