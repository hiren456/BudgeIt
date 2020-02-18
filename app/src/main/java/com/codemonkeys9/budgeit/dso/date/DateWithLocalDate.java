package com.codemonkeys9.budgeit.dso.date;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

public class DateWithLocalDate implements Date {

    LocalDate date;

    DateWithLocalDate(String date) {
        if(date == null){

            throw new NullPointerException();
        }else if(date.equals("now")){

            this.date = LocalDate.now();
        } else if(date.equals("past")) {

            this.date = LocalDate.parse("1970-01-01");
        }else{

            this.date = LocalDate.parse(date);
        }
    }

    DateWithLocalDate(int year, int month, int day) {
        this.date = LocalDate.of(year,month,day);
    }


    @Override
    public String getDisplay() {
        String formattedDate = this.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
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
    public int compareTo(Date o) {
        LocalDate comp = LocalDate.of(o.getYear(),o.getMonth(),o.getDay());
        return this.date.compareTo(comp);
    }
}
