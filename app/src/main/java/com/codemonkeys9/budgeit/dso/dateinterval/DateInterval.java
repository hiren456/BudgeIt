package com.codemonkeys9.budgeit.dso.dateinterval;

import com.codemonkeys9.budgeit.dso.date.Date;

public interface DateInterval {
    /*
    Gets the date that the interval starts at
     */
    Date getStart();

    /*
    Gets the date that the interval ends at
     */
    Date getEnd();

    /*
    Checks whether a date is within the interval
    if the date is on the start or end date it is
    considered to be "in" the interval
     */
    boolean in(Date date);

}
