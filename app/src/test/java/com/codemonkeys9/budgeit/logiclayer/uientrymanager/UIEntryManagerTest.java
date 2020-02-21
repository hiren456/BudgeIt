package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.database.DatabaseHolder;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UIEntryManagerTest {
    @Before
    public void resetDatabase() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = DatabaseHolder.class.getDeclaredField("db");
        instance.setAccessible(true);
        instance.set(null, null);
        DatabaseHolder.init();
    }
    @Test
    public void createEntryWithoutflagTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();

        String amount = "-99.99";
        String details = "Food";

    }

    @Test
    public void createEntryWithflagTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void flagUnflaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void flagFlaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void unflagUnflaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void unflagFlaggedEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void unflagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }

    @Test
    public void flagNonexistentEntryTest(){
        UIEntryManager manager = UIEntryManagerFactory.createUIEntryManager();


    }
}
