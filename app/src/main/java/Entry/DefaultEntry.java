package Entry;

import java.util.Date;

class DefaultEntry implements Entry {
    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public int getEntryID() {
        return 0;
    }

    @Override
    public int getCatID() {
        return 0;
    }

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public Entry modifyEntry(int amount, int entryID, int catID, String details, Date date) {
        return null;
    }
}
