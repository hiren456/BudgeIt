package com.codemonkeys9.budgeit.dso.dateinterval;

import com.codemonkeys9.budgeit.dso.date.Date;

public class DateIntervalFactory {
    public static DateInterval fromString(String start,String end){
        return new DefaultDateInterval(start,end);
    }

    public static DateInterval fromDate(Date start, Date end){
        return new DefaultDateInterval(start,end);
    }
}
