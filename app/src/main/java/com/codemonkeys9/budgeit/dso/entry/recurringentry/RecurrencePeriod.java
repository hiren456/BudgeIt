package com.codemonkeys9.budgeit.dso.entry.recurringentry;

public interface RecurrencePeriod {
    int getDays();
    int getWeeks();
    int getMonths();
    int getYears();

    boolean equals(RecurrencePeriod other);
}
