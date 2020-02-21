package com.codemonkeys9.budgeit.dso.dateinterval;

import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.exceptions.InvalidDateIntervalException;

class DefaultDateInterval implements DateInterval {
    Date start;
    Date end;

    public DefaultDateInterval(String start, String end) {
        this.start = DateFactory.fromString(start);
        this.end = DateFactory.fromString(end);

        // if start is later then end throw an exception
        if(this.start.compareTo(this.end) > 0){

            throw new InvalidDateIntervalException(start+"-"+end+"is not a valid interval");
        }
    }

    public DefaultDateInterval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Date getStart() {
        return this.start;
    }

    @Override
    public Date getEnd() {
        return this.end;
    }

    @Override
    public boolean in(Date date) {
        boolean afterStart = (date.compareTo(start) >= 0);
        boolean beforeEnd = (date.compareTo(end) <= 0);
        return afterStart && beforeEnd;
    }

    @Override
    public boolean equals(DateInterval other) {
        return this.start.equals(other.getStart())
                && this.end.equals(other.getEnd());
    }
}
