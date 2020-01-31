package Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Entry.Entry;

class StubDatabase implements Database {

    // This is where all the entrys are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,Entry> entryMap;

    //private int catCounter;

    private int idCounter;


    StubDatabase(int initialIDCounter){
        this.entryMap = new HashMap<Integer,Entry>();
        //this.catCounter = initialCatCounter;

        this.idCounter = initialIDCounter;
    }
    @Override
    public void insertEntry(Entry entry) {
        // TODO: Ensure valid entry

        // Checks if an entry with the same key is already in
        // the database
        if(entryMap.containsKey(entry.getEntryID())){

            // TODO: throw exception
        }else{

            this.entryMap.put(entry.getEntryID(),entry);
        }

        // TODO: test to make sure entry is in the list
        // TODO: test to make sure an exception is thrown
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

    //@Override
    //public List<Entry> selectByCategory(int catID) {
    //    // TODO: ensure valid parameters
    //    ArrayList<Entry> returnList = new ArrayList<Entry>();

    //    // find all entries within the specified range
    //    for ( Entry entry : this.entryMap.values()){
    //        int entryCatID = entry.getCatID();

    //        // might be a bug if this does not include start date
    //        if(entryCatID == catID){

    //            returnList.add(entry);
    //        }
    //    }


    //    // sort the entries by date
    //    Collections.sort(returnList,new SortByDate());

    //    // TODO: test for proper list
    //    // TODO: test for proper order
    //    return returnList;
    //}

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
        // TODO: test for proper id counter
        return this.idCounter;
    }

    @Override
    public void updateIDCounter(int newCounter) {
        // TODO: ensure valid parameters

        // TODO: test that it is actually updated
        this.idCounter = newCounter;
    }
}
