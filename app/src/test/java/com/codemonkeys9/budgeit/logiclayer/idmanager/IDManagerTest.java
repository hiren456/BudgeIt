package com.codemonkeys9.budgeit.logiclayer.idmanager;

import com.codemonkeys9.budgeit.database.DatabaseFactory;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.exceptions.InvalidIDTypeException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class IDManagerTest {
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
    }

    @Test
    public void getInitialIDEntryTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        assertEquals(1,idManager.getInitialID("Entry"));
    }

    @Test
    public void getInitialIDCategoryTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        assertEquals(1,idManager.getInitialID("Category"));
    }

    @Test
    public void getInitialIDInvalidIDTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        try{
            idManager.getInitialID("Potato");
            fail();
        }catch (InvalidIDTypeException e){

        }catch (Exception e ){
            fail();
        }
    }

    @Test
    public void getDefaultIDEntryTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        // Entry does not have a default value
        try{
            idManager.getDefaultID("Entry");
            fail();
        }catch (InvalidIDTypeException e){

        }catch (Exception e ){
            fail();
        }
    }

    @Test
    public void getDefaultIDCategoryTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        assertEquals(0,idManager.getDefaultID("Category"));
    }

    @Test
    public void getDefualtIDInvalidIDTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        try{
            idManager.getInitialID("Potato");
            fail();
        }catch (InvalidIDTypeException e){

        }catch (Exception e ){
            fail();
        }
    }

    @Test
    public void uniqueEntryIDTest(){
        IDManager idManager = IDManagerFactory.createIDManager();
        int curr = idManager.getNewID("Entry");
        int next  = idManager.getNewID("Entry");
        for (int i = 0; i>20;i++){
            assertNotEquals(curr,next);
            next = curr;
            curr = idManager.getNewID("Entry");
        }
    }

    @Test
    public void uniqueCategoryIDTest(){
        IDManager idManager = IDManagerFactory.createIDManager();
        int curr = idManager.getNewID("Category");
        int next  = idManager.getNewID("Category");
        for (int i = 0; i>20;i++){
            assertNotEquals(curr,next);
            next = curr;
            curr = idManager.getNewID("Category");
        }
    }
    @Test
    public void invalidNewIDTest(){
        IDManager idManager = IDManagerFactory.createIDManager();

        try{
            idManager.getNewID("Potato");
            fail();
        }catch (InvalidIDTypeException e){

        }catch (Exception e ){
            fail();
        }
    }
}
