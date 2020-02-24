package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.category.Category;
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

    // This is where all the categories are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,Category> categoryMap;

    private HashMap<String,Integer> ids;


    StubDatabase(int initialEntryID,int initialCategoryID){

        this.entryMap = new HashMap<Integer,Entry>();
        this.categoryMap = new HashMap<Integer, Category>();

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
        if(entryMap.containsKey(entry.getEntryID())){
            this.entryMap.put(entry.getEntryID(),entry);
        }else{
            isUpdated = false;
        }

        return isUpdated;
    }

    @Override
    public List<Entry> getAllEntries() {
        return new ArrayList<>(entryMap.values());
    }

    @Override
    public List<Entry> getEntriesByCategoryID(int ID){
        ArrayList<Entry> returnList = new ArrayList<Entry>();

        // find all entries with the same category ID
        for ( Entry entry : this.entryMap.values()){
            if (entry.getCatID() == ID){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
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

        Entry removed = this.entryMap.remove(ID);
        return removed != null;
    }


    @Override
    public void insertCategory(Category category) {

        //Checks if a category with the same key is already in the database
        if(categoryMap.containsKey(category.getID())){
            throw new RuntimeException("The category you try to insert is already inserted");
        }else{
            this.categoryMap.put(category.getID(),category);
        }
    }

    @Override
    public boolean updateCategory(Category category) {

        boolean isUpdated = true;

        // Checks if a category with the same key is already in the database
        if(categoryMap.containsKey(category.getID())){
            this.categoryMap.put(category.getID(),category);
        }else{
            isUpdated = false;
        }

        return isUpdated;
    }

    @Override
    public Category selectCategoryByID(int ID) {
        return this.categoryMap.get(ID);
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryMap.values());
    }

    @Override
    public boolean deleteCategory(int ID) {

        Category removed = this.categoryMap.remove(ID);
        return removed != null;
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
