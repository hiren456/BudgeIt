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
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,false));
    }

    public void createPurchase(Amount amount, Details details, Date date,boolean flag){
        // should be in its own class
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(PurchaseFactory.createPurchase(amount,entryID,details,date,flag));
    }

    public void createIncome(Amount amount, Details details, Date date){
        // should be in its own class
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(IncomeFactory.createIncome(amount,entryID,details,date));
    }
}

