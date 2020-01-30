package Database;

import java.util.Date;
import java.util.List;

import Entry.Entry;

public interface Database {

    void insertEntry(Entry entry);
    Entry selectByID(int ID);
    List<Entry> selectByDate(Date startDate, Date endDate);
    List<Entry> selectByCategory(int catID);
    boolean deleteEntry(int ID);

    // returns current entry id counter
    int getIDCounter();

    // updates entry id counter
    void updateIDCounter(int newCounter);
}
