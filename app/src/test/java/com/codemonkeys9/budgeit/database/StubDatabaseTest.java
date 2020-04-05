package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import org.junit.Before;
import org.junit.Test;

public class StubDatabaseTest extends DatabaseTest{
    @Before
    public void createDb() {
        IDManager idManager = IDManagerFactory.createIDManager();
        DatabaseHolder.initTestable(DatabaseFactory.createStubDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category")));
        this.db = DatabaseHolder.getDatabase();
    }

    @Override
    @Test
    public void updateDateLastCheckedForCategoryTest(){
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }

    @Override
    @Test
    public void updateDateLastCheckedForRecurringTest() {
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }

    @Override
    @Test
    public void initialDateLastCheckedTest(){
        // This functionality is unneeded in the stub as
        // the tests that use it use mockito
    }

}
