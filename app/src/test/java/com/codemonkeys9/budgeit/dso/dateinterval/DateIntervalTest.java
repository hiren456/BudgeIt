package com.codemonkeys9.budgeit.dso.dateinterval;

import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateIntervalTest {
    @Test
    public void validIntervalFromStringTest(){
        Date start = DateFactory.fromString("2000-01-01");
        Date end = DateFactory.fromString("2001-01-01");
        DateInterval interval = DateIntervalFactory.fromString("2000-01-01","2001-01-01");

        assertTrue(start.equals(interval.getStart()));
        assertTrue(end.equals(interval.getEnd()));
    }

    @Test
    public void validIntervalFromDateTest(){
        Date start = DateFactory.fromString("2000-01-01");
        Date end = DateFactory.fromString("2001-01-01");
        DateInterval interval = DateIntervalFactory.fromDate(start,end);

        assertTrue(start.equals(interval.getStart()));
        assertTrue(end.equals(interval.getEnd()));
    }

    @Test
    public void inTest(){
        Date start = DateFactory.fromString("2000-01-01");
        Date in = DateFactory.fromString("2001-01-01");
        Date end = DateFactory.fromString("2001-06-02");
        DateInterval interval = DateIntervalFactory.fromDate(start,end);

        assertTrue(interval.in(in));
    }

    @Test
    public void inOutTest(){
        Date start = DateFactory.fromString("2000-01-01");
        Date end = DateFactory.fromString("2001-01-01");
        Date out = DateFactory.fromString("2001-06-02");
        DateInterval interval = DateIntervalFactory.fromDate(start,end);

        assertFalse(interval.in(out));
    }

    @Test
    public void inOnTest(){
        Date start = DateFactory.fromString("2000-01-01");
        Date end = DateFactory.fromString("2001-06-02");
        DateInterval interval = DateIntervalFactory.fromDate(start,end);

        assertTrue(interval.in(start));
        assertTrue(interval.in(end));
    }
}
