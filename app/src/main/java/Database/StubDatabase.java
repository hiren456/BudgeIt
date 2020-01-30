package Database;

import java.util.Date;
import java.util.List;

import Entry.Entry;

class StubDatabase implements Database {
    @Override
    public void insertEntry(Entry entry) {

    }

    @Override
    public Entry selectByID(int ID) {
        return null;
    }

    @Override
    public List<Entry> selectByDate(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Entry> selectByCategory(int catID) {
        return null;
    }

    @Override
    public boolean deleteEntry(int ID) {
        return false;
    }

    @Override
    public int getIDCounter() {
        return 0;
    }

    @Override
    public void updateIDCounter(int newCounter) {

    }
}
