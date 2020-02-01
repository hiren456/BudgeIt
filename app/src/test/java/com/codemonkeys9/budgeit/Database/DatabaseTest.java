package com.codemonkeys9.budgeit.Database;


import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void idCounterInitialValueTest() {
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);

        assertEquals("When a database is initialized, " +
                "the intitial value for the id counter, passed as a parameter," +
                "is not what is returned by getIDCounter.",database.getIDCounter(), 42);
    }

    @Test
    public void idCounterUpdateValueTest() {
        int intitialIDCounter = 42;
        Database database = new DatabaseFactory().createDatabase(intitialIDCounter);
        database.updateIDCounter(23);

        assertEquals("When updateIDCounter is called, " +
                "the id counter returned by getIDCounter" +
                "is not the same as what was passed to updateIDCounter.",database.getIDCounter(), 23);
    }
}
