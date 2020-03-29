package com.codemonkeys9.budgeit.dso.entry.recurringentry;

public class RecurrencePeriodFactory {
    public static RecurrencePeriod createRecurrencePeriod(int days, int weeks, int months, int years) {
        return new DefaultRecurrencePeriod(days, weeks, months, years);
    }
}
