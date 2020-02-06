package com.codemonkeys9.budgeit.LogicLayer.DateParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


class DefaultDateParser implements DateParser {

    @Override
    public Date parseDate(String date) {
        Date parsedDate = null;
        if (date.equals("past")) {
            parsedDate = new Date(0);
        } else if ( date.equals("now")){
                parsedDate = new Date();
        }else {
            try {
                parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return parsedDate;
    }
}
