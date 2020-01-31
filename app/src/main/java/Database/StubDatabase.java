package Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Entry.Entry;


class StubDatabase implements Database {

    HashMap<Integer,Entry> entryMap;
    int catCounter;
    int idCounter;
    StubDatabase(int initialIDCounter, int initialCatCounter){
        this.entryMap = new HashMap<Integer,Entry>();
        this.catCounter = initialCatCounter;
        this.idCounter = initialIDCounter;
    }
    @Override
    public void insertEntry(Entry entry) {
        // TODO: Ensure proper entry
        // TODO: Make sure entry with same id is not in list
        if(entryMap.containsKey(entry.getAmount())){

            //exception
        }else{

            this.entryMap.put(entry.getAmount(),entry);
        }

        // TODO: test to make sure entry is in the list
    }

    @Override
    public Entry selectByID(int ID) {
        return this.entryMap.get(ID);
        // TODO: test to make sure the proper entry is returned
    }

    @Override
    public List<Entry> selectByDate(Date startDate, Date endDate) {
        // TODO: ensure valid parameters
        ArrayList<Entry> returnList = new ArrayList<Entry>();

        // find all entries within the specified range
        for ( Entry entry : this.entryMap.values()){
            Date date = entry.getDate();

            // might be a bug if this does not include start date
            if(date.before(endDate) && date.after(startDate)){

                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList,new SortByDate());

        // TODO: test for proper list
        // TODO: test for proper order
        return returnList;
    }

    @Override
    public List<Entry> selectByCategory(int catID) {
        // TODO: ensure valid parameters
        ArrayList<Entry> returnList = new ArrayList<Entry>();

        // find all entries within the specified range
        for ( Entry entry : this.entryMap.values()){
            int entryCatID = entry.getCatID();

            // might be a bug if this does not include start date
            if(entryCatID == catID){

                returnList.add(entry);
            }
        }


        // sort the entries by date
        Collections.sort(returnList,new SortByDate());

        // TODO: test for proper list
        // TODO: test for proper order
        return returnList;
    }

    @Override
    public boolean deleteEntry(int ID) {
        Entry removed = this.entryMap.remove(ID);

        if(removed == null){

            return false;
        }else{

            return true;
        }
    }

    @Override
    public int getIDCounter() {
        return this.idCounter;
    }

    @Override
    public void updateIDCounter(int newCounter) {
        this.idCounter = newCounter;
    }
}

class SortByDate implements Comparator<Entry> {
    public int compare(Entry a, Entry b) {
        return a.getDate().compareTo(b.getDate());
    }
}
