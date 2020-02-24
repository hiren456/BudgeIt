package com.codemonkeys9.budgeit.dso.date;

public interface Date extends Comparable<Date> {

    /*
    Gets a date in the form of "Apr 3, 2019"
     */
    String getDisplay();

    /*
    Gets the year as an int
    ie. the year 1999 would be returned as 1999
     */
    int getYear();

    /*
    Gets the month as an int
    ie. the month january would be returned as 1
     */
    int getMonth();

    /*
    Gets the day as an int
    ie. the first day of the month would be returned as 1
     */
    int getDay();

    boolean equals(Date other);
}
