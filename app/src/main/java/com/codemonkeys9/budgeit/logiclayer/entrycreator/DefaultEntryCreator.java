package com.codemonkeys9.budgeit.logiclayer.entrycreator;


import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.entry.EntryFactory;

class DefaultEntryCreator implements EntryCreator {

    Database database;

    DefaultEntryCreator() {
        this.database = DatabaseHolder.getDatabase();
    }

    public void createEntry(Amount amount, Details details, Date date){
        // should be in its own class
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(EntryFactory.createEntry(amount,entryID,details,date));
    }
}

