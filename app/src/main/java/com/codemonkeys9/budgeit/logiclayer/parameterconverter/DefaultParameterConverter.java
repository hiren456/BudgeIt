package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.entry.Entry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

public class DefaultParameterConverter implements ParameterConverter {

    Validator validator;
    DefaultParameterConverter(){
        this.validator = new Validator();
    }

    @Override
    public String createDisplayAmount(int amount) {
        String string = Integer.toString(amount);

        // Pad with zero's if less then two digits
        if (string.length() == 1){
            string = string + "0";
        }
        if (string.length() == 0){
            string = string + "00";
        }

        String out = string.substring(0,string.length()-2);
        out += ".";
        out += string.substring(string.length() -2);
        return out;
    }

    @Override
    public String createDisplayDate(LocalDate date) {
        String displayDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        return displayDate;
    }

    @Override
    public int parseDisplayAmount(String displayAmount) {
        validator.validateAmount(displayAmount);

        // parse string into double then remove decimal and store as whole number
        // eg. "100.92" gets turned into 10092
        int out = (int) (Double.parseDouble(displayAmount) * 100 );

        return out;
    }

    @Override
    public LocalDate parseDate(String date) {
        validator.validateDate(date);

        LocalDate parsedDate = null;

        if (date.equals("past")) {
            parsedDate = LocalDate.MIN;
        } else if ( date.equals("now")){
            parsedDate = LocalDate.now();
        }else {
            parsedDate = LocalDate.parse(date);
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
