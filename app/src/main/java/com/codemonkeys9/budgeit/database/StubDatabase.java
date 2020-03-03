package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.CategoryDateComparator;
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


    /*
    Inserts an Entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    @Override
    public void insertEntry(Entry entry) {

        //Checks if an entry with the same key is already in the database
        if(entryMap.containsKey(entry.getEntryID())){
            throw new RuntimeException("The entry you try to insert is already inserted");
        }else{
            this.entryMap.put(entry.getEntryID(),entry);
        }

    }

    /*
    Update the entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
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

    /*
    return a list of all entries sorted by date
    or an empty list if db is empty
     */
    @Override
    public List<Entry> getAllEntries() {
        List<Entry> entList = new ArrayList<>(entryMap.values());
        // sort the entries by date
        Collections.sort(entList,new EntryDateComparator());
        return entList;
    }


    /*
    return a list of entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
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


    /*
    return an entry by ID
    if not found returns null
     */
    @Override
    public Entry selectByID(int ID) {
        return this.entryMap.get(ID);
    }


    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
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


    /*
    delete an entry and return true if the entry deleted successfully,
    otherwise return false
     */
    @Override
    public boolean deleteEntry(int ID) {

        Entry removed = this.entryMap.remove(ID);
        return removed != null;
    }


    /*
    Inserts an Category into the database.
    If the category with the same ID is in the db throws runtime exception
     */
    @Override
    public void insertCategory(Category category) {

        //Checks if a category with the same key is already in the database
        if(categoryMap.containsKey(category.getID())){
            throw new RuntimeException("The category you try to insert is already inserted");
        }else{
            this.categoryMap.put(category.getID(),category);
        }
    }


    /*
    Update the Category
    return true if the category is found in the database and then updated, otherwise return false
     */
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


    /*
    return a category by ID
    if not found returns null
     */
    @Override
    public Category selectCategoryByID(int ID) {
        return this.categoryMap.get(ID);
    }


    /*
    returns a list of all Categories sorted by date
    or an empty list if db is empty
     */
    @Override
    public List<Category> getAllCategories() {
        List<Category> catList = new ArrayList<>(categoryMap.values());
        Collections.sort(catList,new CategoryDateComparator()); // sort the categories by date
        return catList;
    }


    /*
    delete a category and return true if the category was deleted successfully,
    otherwise return false
     */
    @Override
    public boolean deleteCategory(int ID) {
        Category removed = this.categoryMap.remove(ID);
        return removed != null;
    }


    /*
      returns current entry id counter
      Possible idNames are "Entry" and "Category"
      */
    @Override
    public int getIDCounter(String idName) {
        return this.ids.get(idName);
    }


    /*
     updates entry id counter
     */
    @Override
    public void updateIDCounter(String idName, int newCounter) {
        this.ids.put(idName,newCounter);
    }
}
