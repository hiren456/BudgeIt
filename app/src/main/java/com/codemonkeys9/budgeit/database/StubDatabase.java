package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.CategoryDateComparator;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.entry.EntryDateComparator;
import com.codemonkeys9.budgeit.dso.entry.RecurringEntry;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class StubDatabase implements Database {

    // This is where all the default entries are stored
    // they are searched and inserted by their ID
    private HashMap<Integer, BaseEntry> defaultEntryMap;

    // This is where all the recurring entries are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,RecurringEntry> recurringEntryMap;

    // This is where all the categories are stored
    // they are searched and inserted by their ID
    private HashMap<Integer,Category> categoryMap;

    private HashMap<String,Integer> ids;


    StubDatabase(int initialEntryID,int initialCategoryID){

        this.defaultEntryMap = new HashMap<Integer, BaseEntry>();
        this.recurringEntryMap = new HashMap<Integer,RecurringEntry>();
        this.categoryMap = new HashMap<Integer, Category>();

        this.ids = new HashMap<String,Integer>();
        this.ids.put("Entry",initialEntryID);
        this.ids.put("Category",initialCategoryID);
    }


    @Override
    public Date getDateLastChecked(String type) {
        return null;
    }

    @Override
    public boolean updateDateLastChecked(String type, Date date) {
        return false;
    }

    /*
        Inserts an Entry into the database.
        If the Entry with the same ID is in the db throws runtime exception
         */
    @Override
    public void insertDefaultEntry(Entry entry) {
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        //Checks if an entry with the same key is already in the database
        if(defaultEntryMap.containsKey(entry.getEntryID())){
            throw new RuntimeException("The entry you try to insert already exists in the database!");
        }else if (entry.getCatID() != defaultCatID && !categoryMap.containsKey(entry.getCatID())){
            throw new RuntimeException("There is no category with this catID to insert the entry");
        }else{
            this.defaultEntryMap.put(entry.getEntryID(),entry);
        }

    }

    /*
    Update the entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    @Override
    public boolean updateDefaultEntry(BaseEntry entry) {
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        boolean isUpdated = true;
        boolean isCatID = categoryMap.containsKey(entry.getCatID()) || entry.getCatID() == defaultCatID;

        // Checks if an entry with the same key is already in the database
        if( defaultEntryMap.containsKey(entry.getEntryID()) && isCatID){
            this.defaultEntryMap.put(entry.getEntryID(),entry);
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
    public List<BaseEntry> getAllDefaultEntries() {
        List<BaseEntry> entList = new ArrayList<>(defaultEntryMap.values());
        // sort the entries by date
        Collections.sort(entList,new EntryDateComparator());
        return entList;
    }


    /*
    return a list of entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    @Override
    public List<BaseEntry> getDefaultEntriesByCategoryID(int ID){
        ArrayList<BaseEntry> returnList = new ArrayList<>();

        // find all entries with the same category ID
        for ( BaseEntry entry : this.defaultEntryMap.values()){
            if (entry.getCatID() == ID){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList, new EntryDateComparator());
        return returnList;
    }


    /*
    return an entry by ID
    if not found returns null
     */
    @Override
    public BaseEntry selectDefaultEntryByID(int ID) {
        return this.defaultEntryMap.get(ID);
    }


    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    @Override
    public List<BaseEntry> selectDefaultEntriesByDate(DateInterval dateInterval) {
        ArrayList<BaseEntry> returnList = new ArrayList<>();

        // find all entries within the specified range
        for ( BaseEntry entry : this.defaultEntryMap.values()){
            Date date = entry.getDate();

            if(dateInterval.in(date)){
                returnList.add(entry);
            }
        }

        // sort entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
    }


    /*
    returns the list of default entries from that fall within the dateInterval
    and by category specified category ID, returns empty list if the are no entries
     */
    @Override
    public List<BaseEntry> selectDefaultEntriesByDateAndCategoryID(DateInterval dateInterval, int catID){
        // find all entries that fall within date interval
        List<BaseEntry> entryByDateList = this.selectDefaultEntriesByDate(dateInterval);
        ArrayList<BaseEntry> returnList = new ArrayList<>();

        // find all entries with the same category ID
        for ( BaseEntry entry : entryByDateList){
            if (entry.getCatID() == catID){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList, new EntryDateComparator());
        return returnList;
    }


    /*
    delete an entry and return true if the entry deleted successfully,
    otherwise return false
     */
    @Override
    public boolean deleteDefaultEntry(int ID) {

        BaseEntry removed = this.defaultEntryMap.remove(ID);
        return removed != null;
    }


    /*
    Inserts a recurring entry into the database.
    If the entry with the same ID is in the db throws runtime exception
     */
    @Override
    public void insertRecurringEntry(RecurringEntry entry) {
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        //Checks if an entry with the same key is already in the database
        if(recurringEntryMap.containsKey(entry.getEntryID())){
            throw new RuntimeException("The recurring entry you try to insert already exists in the database!");
        }else if (entry.getCatID() != defaultCatID && !categoryMap.containsKey(entry.getCatID())){
            throw new RuntimeException("There is no category with this catID to insert the entry");
        }else{
            this.recurringEntryMap.put(entry.getEntryID(), entry);
        }

    }

    /*
    Update the recurring entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    @Override
    public boolean updateRecurringEntry(RecurringEntry entry) {
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        boolean isUpdated = true;
        boolean isCatID = categoryMap.containsKey(entry.getCatID()) || entry.getCatID() == defaultCatID;

        // Checks if an entry with the same key is already in the database
        if( recurringEntryMap.containsKey(entry.getEntryID()) && isCatID){
            this.recurringEntryMap.put(entry.getEntryID(),entry);
        }else{
            isUpdated = false;
        }

        return isUpdated;
    }

    /*
    return a list of all recurring entries sorted by date
    or an empty list if db is empty
     */
    @Override
    public List<RecurringEntry> getAllRecurringEntries() {
        List<RecurringEntry> entList = new ArrayList<>(recurringEntryMap.values());
        // sort the entries by date
        Collections.sort(entList,new EntryDateComparator());
        return entList;
    }


    /*
    return a list of recurring entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    @Override
    public List<RecurringEntry> getRecurringEntriesByCategoryID(int ID){
        ArrayList<RecurringEntry> returnList = new ArrayList<RecurringEntry>();

        // find all entries with the same category ID
        for (RecurringEntry entry : this.recurringEntryMap.values()){
            if (entry.getCatID() == ID){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
    }


    /*
    return a recurring entry by ID
    if not found returns null
     */
    @Override
    public RecurringEntry selectRecurringEntryByID(int ID) {
        return this.recurringEntryMap.get(ID);
    }


    /*
    returns the list of recurring entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    @Override
    public List<RecurringEntry> selectRecurringEntriesByDate(DateInterval dateInterval) {
        ArrayList<RecurringEntry> returnList = new ArrayList<RecurringEntry>();

        // find all entries within the specified range
        for ( RecurringEntry entry : this.recurringEntryMap.values()){
            Date date = entry.getDate();

            if(dateInterval.in(date)){
                returnList.add(entry);
            }
        }

        // sort entries by date
        Collections.sort(returnList,new EntryDateComparator());

        return returnList;
    }


    /*
    returns the list of recurring entries from that fall within the dateInterval
    and by category specified category ID, returns empty list if the are no entries
     */
    @Override
    public List<RecurringEntry> selectRecurringEntriesByDateAndCategoryID(DateInterval dateInterval, int catID){
        // find all entries that fall within date interval
        List<RecurringEntry> entryByDateList = this.selectRecurringEntriesByDate(dateInterval);
        ArrayList<RecurringEntry> returnList = new ArrayList<RecurringEntry>();

        // find all entries with the same category ID
        for (RecurringEntry entry : entryByDateList ){
            if (entry.getCatID() == catID){
                returnList.add(entry);
            }
        }

        // sort the entries by date
        Collections.sort(returnList, new EntryDateComparator());
        return returnList;
    }


    /*
    delete a recurring entry and return true if the entry deleted successfully,
    otherwise return false
     */
    @Override
    public boolean deleteRecurringEntry(int ID) {

        RecurringEntry removed = this.recurringEntryMap.remove(ID);
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

        // Checks if a category with the same id is already in the database
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

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID("Category");

        if (removed != null) {
            //update category also update default entry
            for (BaseEntry entry : this.defaultEntryMap.values()) {
                int catID = entry.getCatID();

                if (catID == ID) {
                    entry = entry.changeCategory(defaultCatID);
                    this.defaultEntryMap.put(entry.getEntryID(), entry);
                }
            }

            //update category also update recurring entry
            for (RecurringEntry entry : this.recurringEntryMap.values()) {
                int catID = entry.getCatID();

                if (catID == ID) {
                    entry = entry.changeCategory(defaultCatID);
                    this.recurringEntryMap.put(entry.getEntryID(), entry);
                }
            }
        }
        return removed != null;
    }


    /*
     returns current entry id counter
     Possible idNames are "Entry" and "Category"
     returns -1 if no such id counter
     */
    @Override
    public int getIDCounter(String idName) {
        int result = -1;
        if(ids.containsKey(idName)){
            result = ids.get(idName);
        }
        return result;
    }


    /*
     updates entry id counter
     */
    @Override
    public void updateIDCounter(String idName, int newCounter) {
        this.ids.put(idName,newCounter);
    }


    /*
    closes the db
     */
    @Override
    public void close() {
        //I am here just to be here
    }

    /*
    deletes everything from tables in the db
     */
    public void clean(){
        defaultEntryMap.clear();
        recurringEntryMap.clear();
        categoryMap.clear();
    }
}
