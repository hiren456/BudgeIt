package com.codemonkeys9.budgeit.dso.entry.recurringentry;

class DefaultRecurrencePeriod implements RecurrencePeriod {
    int days, weeks, months, years;

    DefaultRecurrencePeriod(int days, int weeks, int months, int years) {
        this.days = days;
        this.weeks = weeks;
        this.months = months;
        this.years = years;
    }

    @Override
    public int getDays() { return days; }
    @Override
    public int getWeeks() { return weeks; }
    @Override
    public int getMonths() { return months; }
    @Override
    public int getYears() { return years; }
}
