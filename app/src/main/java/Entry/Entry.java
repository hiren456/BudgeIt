package Entry;

import java.util.Date;

public interface Entry {

    // not allowed in interface
    //Entry.Entry(int amount,int entryID,int catID, String details, Date date);

    // getters
    int getAmount();
    int getEntryID();
    //int getCatID();
    String getDetails();
    Date getDate();

    // takes values and returns an entry
    // with those updated updated values
    Entry modifyEntry(int amount,String details, Date date);
}
