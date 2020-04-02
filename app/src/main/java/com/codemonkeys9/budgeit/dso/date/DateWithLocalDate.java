package com.codemonkeys9.budgeit.dso.date;

import com.codemonkeys9.budgeit.exceptions.InvalidDateException;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

public class DateWithLocalDate implements Date {

    LocalDate date;

    DateWithLocalDate(String date) {
        if(date == null){
            throw new NullPointerException("date is null");
        }else if(date.equals("now")){
            this.date = LocalDate.now();
        } else if(date.equals("past")) {
            this.date = LocalDate.parse("1970-01-01");
        }else{
            try {
                this.date = LocalDate.parse(date);
            } catch (Exception e) {
                throw new InvalidDateException("Date String "+date+" is invalid.");
            }
        }
    }

    DateWithLocalDate(int year, int month, int day) {
        this.date = LocalDate.of(year,month,day);
    }


    @Override
    public String getDisplay() {
        String formattedDate = this.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        return formattedDate;
    }

    @Override
    public int getYear() {
        return this.date.getYear();
    }

    @Override
    public int getMonth() {
        return this.date.getMonthValue();
    }

    @Override
    public int getDay() {
        return this.date.getDayOfMonth();
    }

    @Override
    public boolean equals(Date other) {
        return getDay() == other.getDay()
                && getMonth() == other.getMonth()
                && getYear() == other.getYear();
    }

    @Override
    public DateWithLocalDate clone() {
        return new DateWithLocalDate(getYear(), getMonth(), getDay());
    }

    @Override
    public boolean inFuture() {
        LocalDate now = LocalDate.now();
        return this.date.isAfter(now);
    }

    @Override
    public int compareTo(Date o) {
        LocalDate comp = LocalDate.of(o.getYear(),o.getMonth(),o.getDay());
        return this.date.compareTo(comp);
    }
}
