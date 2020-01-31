package LogicLayer.EntryCreator;
import java.util.Date;
import java.text.SimpleDateFormat;
import Database.Database;
import Entry.EntryFactory;

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

        parsedAmount = Integer.parseInt(amount);
        int entryID = database.getIDCounter();
        database.updateIDCounter(entryID + 1);

        database.insertEntry(this.entryFactory.createEntry(parsedAmount,entryID,details,parsedDate));
    }
}

