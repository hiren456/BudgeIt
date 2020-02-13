package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.entry.DisplayConverter;
import com.codemonkeys9.budgeit.entry.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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


    @Override
    public List<Entry> createDisplayEntryList(List<Entry> list) {
        // converts list from chronological to reverse chronological
        Collections.reverse(list);
        return list;
    }
}
