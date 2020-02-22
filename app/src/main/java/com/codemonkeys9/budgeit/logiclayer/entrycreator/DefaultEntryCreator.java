package com.codemonkeys9.budgeit.logiclayer.entrycreator;


import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;

class DefaultEntryCreator implements EntryCreator {

    Database database;

    DefaultEntryCreator() {
        this.database = DatabaseHolder.getDatabase();
    }

    public void createPurchase(Amount amount, Details details, Date date){
        // should be in its own class
        int entryID = database.getIDCounter("Entry");
        database.updateIDCounter("Entry",entryID + 1);

        // replace with IDGENERATOR.getDefaultCatID
        int catID = 0;

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,catID,false));
    }

    public void createPurchase(Amount amount, Details details, Date date,boolean flag){
        // should be in its own class
        int entryID = database.getIDCounter("Entry");
        database.updateIDCounter("Entry",entryID + 1);

        // replace with IDGENERATOR.getDefaultCatID
        int catID = 0;

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,catID,flag));
    }

    public void createIncome(Amount amount, Details details, Date date){
        // should be in its own class
        int entryID = database.getIDCounter("Entry");
        database.updateIDCounter("Entry",entryID + 1);

        // replace with IDGENERATOR.getDefaultCatID
        int catID = 0;

        database.insertEntry(IncomeFactory.createIncome(amount,entryID,details,date,catID));
    }

    // TODO: create ability to specify catID from the get go
}

