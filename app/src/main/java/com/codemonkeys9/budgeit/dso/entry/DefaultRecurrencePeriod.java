package com.codemonkeys9.budgeit.dso.entry;

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

    @Override
    public boolean equals(RecurrencePeriod other) {
        return this.getDays()   == other.getDays()   &&
               this.getWeeks()  == other.getWeeks()  &&
               this.getMonths() == other.getMonths() &&
               this.getYears()  == other.getYears();
    }

    @Override
    public DefaultRecurrencePeriod clone() {
        return new DefaultRecurrencePeriod(days, weeks, months, years);
    }
}
