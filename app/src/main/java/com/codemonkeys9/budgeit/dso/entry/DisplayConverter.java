package com.codemonkeys9.budgeit.dso.entry;

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

    public static int parseDisplayAmount(String displayAmount){
        // parse string into double then remove decimal and store as whole number
        // eg. "100.92" gets turned into 10092
        return (int) (Double.parseDouble(displayAmount) * 100 );
    }
}
