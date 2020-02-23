package com.codemonkeys9.budgeit.logiclayer.entrycreator;


import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

class DefaultEntryCreator implements EntryCreator {

    Database database;
    IDManager idManager;

    DefaultEntryCreator(IDManager idManager) {
        this.database = DatabaseHolder.getDatabase();
        this.idManager = idManager;
    }

    public void createPurchase(Amount amount, Details details, Date date){
        checkFuture(date);
        int entryID = idManager.getNewID("Entry");
        int catID = idManager.getDefaultID("Category");

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,catID,false));
    }

    public void createPurchase(Amount amount, Details details, Date date, boolean flag){
        checkFuture(date);
        int entryID = idManager.getNewID("Entry");
        int catID = idManager.getDefaultID("Category");

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,catID,flag));
    }

    public void createIncome(Amount amount, Details details, Date date){
        checkFuture(date);
        int entryID = idManager.getNewID("Entry");
        int catID = idManager.getDefaultID("Category");

        database.insertEntry(IncomeFactory.createIncome(amount,entryID,details,date,catID));
    }

    private void checkFuture(Date date){
        if(date.inFuture()) { throw new FutureDateException("future date"); }

    }

    // TODO: create ability to specify catID from the get go
}

