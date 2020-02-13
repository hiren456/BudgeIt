package com.codemonkeys9.budgeit.logiclayer.ParameterConverter;

import com.codemonkeys9.budgeit.entry.DisplayConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultParameterConverter implements ParameterConverter {
    @Override
    public String createDisplayAmount(int amount) {
        return DisplayConverter.createDisplayAmount(amount);
    }

    @Override
    public String createDisplayDate(Date date) {
        return DisplayConverter.createDisplayDate(date);
    }

    @Override
    public int parseDisplayAmount(String displayAmount) {
        return DisplayConverter.parseDisplayAmount(displayAmount);
    }

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
