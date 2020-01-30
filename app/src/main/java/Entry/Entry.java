package Entry;

import java.util.Date;

public interface Entry {

    // not allowed in interface
    //Entry.Entry(int amount,int entryID,int catID, String details, Date date);

    // getters
    int getAmount();
    int getEntryID();
    int getCatID();
    String getDetails();
    Date getDate();

    // takes values and returns a modified entry
    // if parameter is Null that indicates not to change that variable
    // leaves original entry untouched
    Entry modifyEntry(int amount,int entryID,int catID, String details, Date date);
}
