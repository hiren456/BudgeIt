package com.codemonkeys9.budgeit.dso.dateintervel;

import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;

class DefaultDateInterval implements DateInterval {
    Date start;
    Date end;

    public DefaultDateInterval(String start, String end) {
        this.start = DateFactory.fromString(start);
        this.end = DateFactory.fromString(end);
        // throw exception
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
}
