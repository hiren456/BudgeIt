package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.exceptions.InvalidDateStringException;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ParameterConverterTest {
    @Test
    public void createDisplayDateValidDateTest() {
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        int month = 10;
        int year = 1999;
        int day = 23;
        Date date = new Date(year - 1900,month - 1,day);

        String expectedDisplayDate = "23 Oct 1999";
        String actualDisplayDate ;

        try{

            actualDisplayDate = converter.createDisplayDate(date);
            assertTrue(actualDisplayDate.equals(expectedDisplayDate));
        }catch (Exception e){
            // should not throw an exception
            fail();
        }
    }

    @Test
    public void parseDisplayDateValidStringTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "23/04/1999";

        int month = 4;
        int year = 1999;
        int day = 23;
        Date expectedDate = new Date(year - 1900,month - 1,day);
        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            assertTrue(expectedDate.equals(actualDate));
        }catch (InvalidDateStringException e ){

            fail("threw invalid date string exception with a valid"+dateString);
        } catch (Exception e ) {

            fail("threw exception with a valid" + dateString);
        }
    }

    @Test
    public void parseDisplayDateValidStringNoLeadingZeroTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "23/4/1999";

        int month = 4;
        int year = 1999;
        int day = 23;
        Date expectedDate = new Date(year - 1900,month - 1,day);
        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            assertTrue(expectedDate.equals(actualDate));
        }catch (InvalidDateStringException e ){

            fail("threw invalid date string exception with a valid"+dateString);
        } catch (Exception e ) {

            fail("threw exception with a valid" + dateString);
        }
    }

    @Test
    public void parseDisplayDateNowTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "now";

        Date expectedDate = new Date();
        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            assertTrue(expectedDate.equals(actualDate));
        }catch (InvalidDateStringException e ){

            fail("threw invalid date string exception with a valid"+dateString);
        } catch (Exception e ) {

            fail("threw exception with a valid" + dateString);
        }
    }

    @Test
    public void parseDisplayDatePastTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "past";

        Date expectedDate = new Date(0);
        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            assertTrue(expectedDate.equals(actualDate));
        }catch (InvalidDateStringException e ){

            fail("threw invalid date string exception with a valid"+dateString);
        } catch (Exception e ) {

            fail("threw exception with a valid" + dateString);
        }
    }

    @Test
    public void parseDisplayDateFutureDateTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "23/04/2030";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("future date does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("future date does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }

    @Test
    public void parseDisplayDateTooManyDaysTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "30/02/2018";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("Feb 30th does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("Feb 30th does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }

    @Test
    public void parseDisplayDateNegDaysTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "-15/02/2018";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("negative day does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("negative day does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }

    @Test
    public void parseDisplayDateNegMonthTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "15/-02/2018";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("negative month does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("negative month does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }

    @Test
    public void parseDisplayDateNegYearTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "15/02/-2018";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("negative year does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("negative year does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }

    @Test
    public void parseDisplayDateNoBackslashesTest(){
        ParameterConverter converter  = ParameterConverterFactory.createParameterConverter();

        String dateString = "15022018";

        Date actualDate;

        try{

            actualDate = converter.parseDate(dateString);
            fail("no backslashes does not throw invalid date string exception");
        }catch (InvalidDateStringException e ){

        } catch (Exception e ) {

            fail("no backslashes does not throw invalid date string exception, it" +
                    "instead throws some other exception");
        }
    }
}

