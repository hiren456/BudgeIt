package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.entry.EntryDateComparator;

import java.util.ArrayList;
import java.util.Collections;;
import java.util.HashMap;
import java.util.List;

class StubDatabase implements Database {

    // This is where all the entries are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,Entry> entryMap;

    private HashMap<String,Integer> ids;


    StubDatabase(int initialEntryID,int initialCategoryID){

        this.entryMap = new HashMap<Integer,Entry>();
        this.ids = new HashMap<String,Integer>();
        this.ids.put("Entry",initialEntryID);
        this.ids.put("Category",initialCategoryID);
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

    @Override
    public List<Entry> selectByDate(DateInterval dateInterval) {
        ArrayList<Entry> returnList = new ArrayList<Entry>();

        // find all entries within the specified range
        for ( Entry entry : this.entryMap.values()){
            Date date = entry.getDate();

            if(dateInterval.in(date)){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
    }

    @Override
    public boolean deleteEntry(int ID) {

        boolean isRemoved = false;
        Entry removed = this.entryMap.remove(ID);

        if(removed == null){
            isRemoved = false;
        }else{
            isRemoved = true;
        }
        return isRemoved;
    }

    @Override
    public int getIDCounter(String idName) {
        return this.ids.get(idName);
    }

    @Override
    public void updateIDCounter(String idName, int newCounter) {
        this.ids.put(idName,newCounter);
    }
}
