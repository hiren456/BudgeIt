package com.codemonkeys9.budgeit.Entry;

import java.util.Date;

public class DisplayConverter {

    public static String createDisplayAmount(int amount) {
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

    public static String createDisplayDate(Date date) {
        String displayDate = date.toGMTString();
        displayDate = displayDate.substring(0,displayDate.length()-13);
        return displayDate;
    }
}
