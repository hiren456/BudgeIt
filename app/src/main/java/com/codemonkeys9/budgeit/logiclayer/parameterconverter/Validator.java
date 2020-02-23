package com.codemonkeys9.budgeit.logiclayer.parameterconverter;
//This class validates all individual inputs made by the user that are passed around as parameters

import com.codemonkeys9.budgeit.exceptions.InvalidAmountStringException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateStringException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class Validator
{
    //Validates an amount entered by the user and returns the amount if its valid
    public void validateAmount(String amount) throws InvalidAmountStringException{
        try{
            Double.parseDouble(amount);
        }catch (Exception e){
            throw new InvalidAmountStringException("Amount "+amount+" is not valid");
        }

    }

    //Validates a date and returns the same date if its valid or else will return null
    public void validateDate(String date) throws InvalidDateStringException
    {
        if(date.equals("past")){

            return;
        }

        if(date.equals("now")){

            return;
        }

        LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateStringException("Date String "+date+" is invalid.");
        }

        int day= parsedDate.getDayOfMonth();
        int month= parsedDate.getMonthValue();
        int year= parsedDate.getYear();

        //boolean validYear =
        //if (day>=1&&day<=31&&(month==1||month==3||month==5||month==7||month==8||month==10||month==12)&&year<=currentYear)
        //{
        //}
        //else if(day>=1&&day<=30&&(month==4||month==6||month==9||month==11)&&year<=currentYear)
        //{
        //}
        //else if(day>=1&&day<=29&&month==2&&year%4==currentYear)
        //{
        //}
        //else if(day>=1&&day<=28&&month==2&&year%4!=0)
        //{
        //}
        //else
        //{
        //    throw new InvalidDateStringException("Date String "+date+" is invalid.");
        //}
    }

    // Judah: I suppose we can just let
    //Validates the interval between two given dates and returns true if its valid or else will return false
    public boolean validateDateInterval(String date1, String date2)throws NumberFormatException,NullPointerException {
        if (date1 != null && date2 != null)
        {
            int currentYear = Year.now().getValue();
            String[] data1 = date1.split("/");

            int day1 = Integer.parseInt(data1[0]);
            int month1 = Integer.parseInt(data1[1]);
            int year1 = Integer.parseInt(data1[2]);

            String[] data2 = date2.split("/");

            int day2 = Integer.parseInt(data2[0]);
            int month2 = Integer.parseInt(data2[1]);
            int year2 = Integer.parseInt(data2[2]);

            if (year1 <= currentYear && year2 <= currentYear && month1 <= month2 && day1 <= day2) {
                return true;
            }else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    // validate a detail string and returns the detail string if its valid or else will return null
    public String validateDetailString(String detailString) throws NullPointerException
    {
        if(detailString!=null)
        {
            return detailString;
        }else
        {
            return null;
        }
    }
}
