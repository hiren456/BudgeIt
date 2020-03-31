package com.codemonkeys9.budgeit.dso.date;

import com.codemonkeys9.budgeit.exceptions.UserInputException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test
    public void pastDateFromStringTest() {
        String stringDate = "past";
        Date date = null;
        try{
            date  = DateFactory.fromString(stringDate);
        }catch (UserInputException e){
            e.printStackTrace();
            fail();
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

        assertTrue(date.equals(DateFactory.fromString("1970-01-01")));
    }

    @Test
    public void compareToTest() {
        String stringDate = "1999-04-23";
        String stringDateAfter = "1999-05-23";
        Date date  = DateFactory.fromString(stringDate);
        Date dateAfter  = DateFactory.fromString(stringDateAfter);

        assertTrue(date.compareTo(dateAfter) < 0);
        assertTrue(dateAfter.compareTo(date) > 0);
    }

    @Test
    public void compareToNowTest() {
        String stringDate = "1999-04-23";
        String stringDateAfter = "now";
        Date date  = DateFactory.fromString(stringDate);
        Date dateAfter  = DateFactory.fromString(stringDateAfter);

        assertTrue(date.compareTo(dateAfter) < 0);
        assertTrue(dateAfter.compareTo(date) > 0);
    }

    @Test
    public void compareToNowAfterTest() {
        String stringDate = "now";
        Date date  = DateFactory.fromString(stringDate);
        Date dateAfter  = DateFactory.fromInts(date.getYear() + 1,date.getMonth(),date.getDay());

        assertTrue(date.compareTo(dateAfter) < 0);
        assertTrue(dateAfter.compareTo(date) > 0);
    }

    @Test
    public void notInFutureTest() {
        String stringDate = "1999-04-23";
        Date date  = DateFactory.fromString(stringDate);

        assertFalse(date.inFuture());
    }

    @Test
    public void inFutureTest() {
        String stringDate = "3000-04-23";
        Date date  = DateFactory.fromString(stringDate);

        assertTrue(date.inFuture());
    }
    @Test
    public void compareToSameTest() {
        String stringDate = "1999-04-23";
        String stringDateSame = "1999-04-23";
        Date date  = DateFactory.fromString(stringDate);
        Date dateSame  = DateFactory.fromString(stringDateSame);

        assertTrue(date.compareTo(dateSame) == 0);
        assertTrue(dateSame.compareTo(date) == 0);
    }

    @Test
    public void stringToStringEqualsTest() {
        String stringDate = "1999-04-23";
        String stringDateSame = "1999-04-23";
        Date date  = DateFactory.fromString(stringDate);
        Date dateSame  = DateFactory.fromString(stringDateSame);

        assertTrue(date.equals(dateSame));
    }

    @Test
    public void intToIntEqualsTest() {
        Date date  = DateFactory.fromInts(1999,04,23);
        Date dateSame  = DateFactory.fromInts(1999,04,23);

        assertTrue(date.equals(dateSame));
    }

    @Test
    public void intToStringEqualsTest() {
        String stringDateSame = "1999-04-23";
        Date date  = DateFactory.fromInts(1999,04,23);
        Date dateSame  = DateFactory.fromString(stringDateSame);

        assertTrue(date.equals(dateSame));
    }

    @Test
    public void getterTest() {
        String stringDate = "1999-04-23";
        Date date  = DateFactory.fromString(stringDate);

        assertEquals(1999,date.getYear());
        assertEquals(04,date.getMonth());
        assertEquals(23,date.getDay());
    }
}
