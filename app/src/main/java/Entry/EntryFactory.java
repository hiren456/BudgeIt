package Entry;

import java.util.Date;

public class EntryFactory {
    public Entry createEntry(int amount, int entryID, String details, Date date){
        return new DefaultEntry(amount, entryID, details, date);
    }
}
