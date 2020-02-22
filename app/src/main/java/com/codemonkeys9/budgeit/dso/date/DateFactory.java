package com.codemonkeys9.budgeit.dso.date;

public class DateFactory {

    /*
    Creates a date object from a string
    the string is excepted to be in
    "yyyy-mm-dd" format.
    The strings, "now" and "past" are also valid
    and return the current date and 1970-01-01
    respectively
     */
    public static Date fromString(String date){
        return new DateWithLocalDate(date);
    }

    /*
    Creates a date object from three ints
    representing the year,month, and day
    the first day of the month is 1 and
    jan is 1
     */
    public static Date fromInts(int year,int month,int day){
        return new DateWithLocalDate(year,month,day);
    }
}
