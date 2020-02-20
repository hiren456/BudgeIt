package com.codemonkeys9.budgeit.dso.date;

import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void validDateFromStringDisplayTest() {
        String stringDate = "1999-04-23";
        Date date  = DateFactory.fromString(stringDate);

        String displayDate = "April 23, 1999";
        assertTrue("Expected: "+displayDate+"\nActual: " + date.getDisplay(),displayDate.equals(date.getDisplay()));
    }

    @Test
    public void nowDateFromStringTest() {
        String stringDate = "now";
        try{
            Date date  = DateFactory.fromString(stringDate);
        }catch (UserInputException e){
            e.printStackTrace();
            fail();
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
