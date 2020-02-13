package com.codemonkeys9.budgeit.logiclayer.entrycreator;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.entry.EntryFactory;

class DefaultEntryCreator implements EntryCreator {

    Database database;

    DefaultEntryCreator(Database database) {
        this.database = database;
    }

    public void createEntry(int amount, String details,Date date){
        // should be in its own class
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(EntryFactory.createEntry(amount,entryID,details,date));
    }
}

