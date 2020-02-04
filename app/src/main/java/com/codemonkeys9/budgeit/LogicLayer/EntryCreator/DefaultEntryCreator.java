package com.codemonkeys9.budgeit.LogicLayer.EntryCreator;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.codemonkeys9.budgeit.Database.Database;
import com.codemonkeys9.budgeit.Entry.EntryFactory;

class DefaultEntryCreator implements EntryCreator {

    private Database database;
    private EntryFactory entryFactory;

    DefaultEntryCreator(Database database) {
        this.database = database;
        this.entryFactory = new EntryFactory();
    }

    @Override
    public void createEntry(String amount, String details, String date) {
        // TODO: through exception if strings are invalid

        int parsedAmount;
        Date parsedDate = null;

        try {
            parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }catch (java.text.ParseException e){

            // Handle invalid date
        }

        // parse string into double then remove decimal and store as whole number
        // eg. "100.92" gets turned into 10092
        parsedAmount = (int) (Double.parseDouble(amount) * 100 );
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(this.entryFactory.createEntry(parsedAmount,entryID,details,parsedDate));
    }
}

