package com.codemonkeys9.budgeit.dso.amount;

import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;

class DefaultAmount implements Amount{
    int amount;

    DefaultAmount(int amount) {
        this.amount = amount;
    }

    DefaultAmount(String amount) {
        this.amount = stringToInt(amount);
    }

    @Override
    public String getDisplay() {
        return intToString(this.amount);
    }

    @Override
    public int getValue() {
        return this.amount;
    }

    /*
    parse string into double then remove decimal and store as whole number
    eg. "100.92" gets turned into 10092
     */
    private int stringToInt(String amount){
        int out;

        try {
            out = (int) (Double.parseDouble(amount) * 100);
        }catch (Exception e){
            throw new InvalidAmountException("Amount "+amount+" is not valid");
        }

        return out;
    }

    /*
    parse an int representing a monetary amount into a string
    eg. 10092 gets turned into "100.92"
     */
    private String intToString(int amount){
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
}
