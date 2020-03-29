package com.codemonkeys9.budgeit.dso.entry;

public interface RecurrencePeriod {
    int getDays();
    int getWeeks();
    int getMonths();
    int getYears();

    boolean equals(RecurrencePeriod other);
    RecurrencePeriod clone();
}
