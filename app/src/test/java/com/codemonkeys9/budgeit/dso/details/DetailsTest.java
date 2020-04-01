package com.codemonkeys9.budgeit.dso.details;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DetailsTest {
    @Test
    public void validDetailsGetValueTest(){
        String stringDetails = "Hello";
        Details details = DetailsFactory.fromString(stringDetails);

        assertTrue(stringDetails.equals(details.getValue()));
    }

    @Test
    public void createDetailFromStringThenModifyOriginStringTest(){
        String stringDetails = "Hello";
        Details details = DetailsFactory.fromString(stringDetails);
        stringDetails = "Goodbye";

        assertTrue("Hello".equals(details.getValue()));
    }

    @Test
    public void modifyGetValueTest(){
        String stringDetails = "Hello";
        Details details = DetailsFactory.fromString(stringDetails);
        String value = details.getValue();
        value = "Goodbye";

        assertTrue("Hello".equals(details.getValue()));
    }
}
