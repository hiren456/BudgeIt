package com.codemonkeys9.budgeit.database;

import android.content.res.Resources;

import com.codemonkeys9.budgeit.entry.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class StubDatabase implements Database {

    // This is where all the entries are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,Entry> entryMap;

    private int idCounter;


    StubDatabase(int initialIDCounter){

        this.entryMap = new HashMap<Integer,Entry>();
        this.idCounter = initialIDCounter;
    }

    @Override
    public void insertEntry(Entry entry) {

        //Checks if an entry with the same key is already in the database
        if(entryMap.containsKey(entry.getEntryID())){
            throw new RuntimeException("The entry you try to insert is already inserted");
        }else{
            this.entryMap.put(entry.getEntryID(),entry);
        }

    }

    //Update the entry
    //return true if the entry is found in the hashmap and then updated, otherwise return false
    @Override
    public boolean updateEntry(Entry entry) {

        boolean isUpdated = true;

        // Checks if an entry with the same key is already in the database
        //Checks if an entry with the same key is already in the database
        if(entryMap.containsKey(entry.getEntryID())){
            this.entryMap.put(entry.getEntryID(),entry);
        }else{
            isUpdated = false;
        }

        return isUpdated;
    }

    //return an entry by ID
    //if not found returns null
    @Override
    public Entry selectByID(int ID) {
        return this.entryMap.get(ID);
    }


    //returns the list of entries from Date startDate till Date endDate
    //returns empty list if the are no entries
    @Override
    public List<Entry> selectByDate(Date startDate, Date endDate) {
        ArrayList<Entry> returnList = new ArrayList<Entry>();

        // find all entries within the specified range
        for ( Entry entry : this.entryMap.values()){
            Date date = entry.getDate();

            if((date.getTime() <= endDate.getTime()) && (date.getTime() >= startDate.getTime())){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
    }


    //returns true if an entry deleted successfully, otherwise return false
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
