package com.codemonkeys9.budgeit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.dso.entrylist.EntryListFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class RealDatabase extends SQLiteOpenHelper implements Database {

    //db constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budget.db";

    //db tables
    private static final String CATEGORY_TABLE = "categories";
    private static final String ENTRY_TABLE = "entries";
    private static final String IDS_TABLE = "ids";

    //categories table attributes
    private static final String CAT_ID = "catID"; //also in entry table
    private static final String CAT_NAME = "catName";
    private static final String CAT_GOAL = "catGoal";
    private static final String CAT_DATE = "catDate";
    private static final String CAT_TYPE = "catType"; // // 0 - budget, 1 - saving

    //entries table attributes
    private static final String ENTRY_ID = "entID";
    private static final String ENTRY_AMOUNT = "entAmount";
    private static final String ENTRY_DATE = "entDate";
    private static final String ENTRY_DETAILS = "entDetails";
    private static final String ENTRY_TYPE = "entType"; // 0 - purchase, 1 - income
    private static final String ENTRY_FLAG = "flag"; // 0 - not falgged, 1 - flagged

    //IDs table attributes
    private static final String ID_NAME = "idName";
    private static final String ID_NUM = "idNum";
    private static final String ID_NAME_CAT = "Category";
    private static final String ID_NAME_ENTRY = "Entry";

    private int initialEntryID;
    private int initialCategoryID;


    public RealDatabase(Context context, int initialEntryID,int initialCategoryID) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.initialEntryID = initialEntryID;
        this.initialCategoryID = initialCategoryID;
        //fill ids table
        fillIDSWhenFirstCreated(initialEntryID, initialCategoryID);
    }

    /*
    Called when constructor is called and creates a db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String catCreateSQL =
                "CREATE TABLE " + CATEGORY_TABLE + " ( " +
                CAT_ID + " INTEGER PRIMARY KEY," + //primary key
                CAT_NAME + " TEXT, " +
                CAT_GOAL + " INTEGER, " +
                CAT_DATE + " TEXT," +
                CAT_TYPE + " INTEGER" + " )";

        String entCreateSQL =
                "CREATE TABLE " + ENTRY_TABLE + " ( " +
                ENTRY_ID + " INTEGER PRIMARY KEY, " + //primary key
                CAT_ID + " INTEGER REFERENCES " + CATEGORY_TABLE + " ON DELETE SET NULL, " + //foreign key
                ENTRY_AMOUNT + " INTEGER, " +
                ENTRY_DETAILS + " TEXT, " +
                ENTRY_DATE + " TEXT, " +
                ENTRY_TYPE + " INTEGER, " +
                ENTRY_FLAG + " INTEGER" + " )";

        String idCreateSQL =
                "CREATE TABLE " + IDS_TABLE + " ( " +
                        ID_NAME + " TEXT PRIMARY KEY," + //primary key
                        ID_NUM + " INTEGER" + " )";

        db.execSQL(catCreateSQL);
        db.execSQL(entCreateSQL);
        db.execSQL(idCreateSQL);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion) {
            //drop old tables and create new
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ENTRY_TABLE);
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        // Enable Foreign Key constraints.
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }


    /*
    Inserts an Entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    public void insertEntry(Entry entry){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int type = 0; //type of the db by default is purchase

        //check if an entry is income
        if (entry instanceof Income){
            type = 1;
            values.putNull(ENTRY_FLAG);
        }else {
            if (((Purchase) entry).flagged()){
                values.put(ENTRY_FLAG, 1);
            }else {
                values.put(ENTRY_FLAG, 0);
            }
        }

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        //what category an entry belong to
        int catID = entry.getCatID();

        //create a row of a new category
        if(catID == defaultCatID){ //checks if entry belong to default category => set null
            values.putNull(CAT_ID);
        }else {
            values.put(CAT_ID, catID);
        }
        values.put(ENTRY_ID, entry.getEntryID());
        values.put(ENTRY_AMOUNT, entry.getAmount().getValue());
        values.put(ENTRY_DETAILS, entry.getDetails().getValue());
        values.put(ENTRY_TYPE, type);

        //the date is in "yyyy-mm-dd" format
        String date = makeDate(entry.getDate().getYear(), entry.getDate().getMonth(), entry.getDate().getDay());
        values.put(ENTRY_DATE, date);

        // insert to db
        long row = db.insert(ENTRY_TABLE,null, values);

        String sql = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CAT_ID + "=" + catID; //prepare query
        Cursor cursor = db.rawQuery(sql, null);
        int count = -1;

        if (cursor != null){
            count = cursor.getCount();
            cursor.close();
        }

        //check for an error
        if(row == -1){
            if(count == 0 && catID != defaultCatID){
                db.close();
                throw new RuntimeException("There is no category with this catID to insert the entry");
            }else {
                db.close();
                throw new RuntimeException("The entry you try to insert already exists in the database!");
            }
        }else {
            db.close();
        }
    }


    //helper method to make supported date
    private String makeDate(int year, int month, int day){
        String date = "" + year + "-";
        String mm = "" + month;
        String dd = "" + day;

        if(month < 10){
            mm = "0" + month;
        }

        if(day < 10){
            dd = "0" + day;
        }

        date = date + mm + "-" + dd;
        return date;
    }


    /*
    Update the entry
    return true if the entry is found in the database and then updated, otherwise return false
     */
    public boolean updateEntry(Entry entry){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        //put a new category for update
        if(entry.getCatID() == defaultCatID){ //checks if entry belong to default category => set null
            values.putNull(CAT_ID);
        }else {
            values.put(CAT_ID, entry.getCatID());
        }

        //check if an entry is purchase
        if (entry instanceof Purchase){
            if (((Purchase) entry).flagged()){
                values.put(ENTRY_FLAG, 1);
            }else {
                values.put(ENTRY_FLAG, 0);
            }
        }



        values.put(ENTRY_AMOUNT, entry.getAmount().getValue());
        values.put(ENTRY_DETAILS, entry.getDetails().getValue());

        //the date is in "yyyy-mm-dd" format
        String date = makeDate(entry.getDate().getYear(), entry.getDate().getMonth(), entry.getDate().getDay());
        values.put(ENTRY_DATE, date);

        // update this entry in the db
        int num = db.update(ENTRY_TABLE, values, ENTRY_ID + "=?",
                new String[]{String.valueOf(entry.getEntryID()) });
        db.close();

        return num > 0;
    }


    /*
    return an entry by ID
    if not found returns null
     */
    public Entry selectByID(int ID){
        Entry entry = null;
        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        String sql = "SELECT * FROM " + ENTRY_TABLE + " WHERE " + ENTRY_ID + "=" + ID; //prepare query

        Cursor cursor = db.rawQuery(sql, null); //exxute query

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        //what category an entry belong to
        int catID = defaultCatID;


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            //get all data from cursor to make an entry object

            //check if catID is not null =>set to existed id, otherwise it is default
            if  (!cursor.isNull(cursor.getColumnIndex(CAT_ID))) {
                catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
            }

            Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
            Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DETAILS)));
            Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));
            int type = cursor.getInt(cursor.getColumnIndex(ENTRY_TYPE));

            //check for purchase or income
            if(type == 0){
                boolean flagged = 1 == cursor.getInt(cursor.getColumnIndex(ENTRY_FLAG));
                entry = PurchaseFactory.createPurchase(amount, ID, details, date, catID, flagged);
            }else{
                entry = IncomeFactory.createIncome(amount, ID, details, date, catID);
            }

            cursor.close();
        }

        db.close();

        return entry;
    }


    /*
    return a list of all entries ordered by date
    or an empty list if db is empty
     */
    public List<Entry> getAllEntries(){
        Entry entry = null;
        ArrayList<Entry> entryList = new ArrayList<Entry>();

        String sql = "SELECT * FROM " + ENTRY_TABLE + " ORDER BY " + ENTRY_DATE; //prepare query

        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        // getting all rows from cursor and filling the list
        if (cursor != null && cursor.getCount()> 0 && cursor.moveToFirst()) {
            do {
                //what category an entry belongs to
                int catID = defaultCatID;

                //get all data from cursor to make an entry object

                //check if catID is not null =>set to existed id, otherwise it is default
                if  (!cursor.isNull(cursor.getColumnIndex(CAT_ID))) {
                    catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                }

                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DETAILS)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));
                int type = cursor.getInt(cursor.getColumnIndex(ENTRY_TYPE));

                //check for purchase or income
                if(type == 0){
                    boolean flagged = 1 == cursor.getInt(cursor.getColumnIndex(ENTRY_FLAG));
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, catID, flagged);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, catID);
                }

                //add entry to the list
                entryList.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return entryList;
    }


    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries ordered by date
     */
    public List<Entry> selectByDate(DateInterval dateInterval){
        Entry entry = null;
        ArrayList<Entry> entryList = new ArrayList<Entry>();

        Date startDate = dateInterval.getStart();
        Date endDate = dateInterval.getEnd();
        String startDateString = makeDate(startDate.getYear(), startDate.getMonth(), startDate.getDay());
        String endDateString = makeDate(endDate.getYear(), endDate.getMonth(), endDate.getDay());


        String sql =
                "SELECT * FROM " + ENTRY_TABLE +
                " WHERE " + ENTRY_DATE + ">='" + startDateString + "' AND " + ENTRY_DATE + "<='" + endDateString +
                "' ORDER BY " + ENTRY_DATE ; //prepare query

        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        //what category an entry belong to
        int catID = defaultCatID;

        // getting all rows from cursor and filling the list
        if (cursor != null && cursor.getCount()> 0 && cursor.moveToFirst()) {
            do {
                //get all data from cursor to make an entry object

                //check if catID is not null =>set to existed id, otherwise it is default
                if  (!cursor.isNull(cursor.getColumnIndex(CAT_ID))) {
                    catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));

                }

                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DETAILS)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));
                int type = cursor.getInt(cursor.getColumnIndex(ENTRY_TYPE));

                //check for purchase or income
                if(type == 0){
                    boolean flagged = 1 == cursor.getInt(cursor.getColumnIndex(ENTRY_FLAG));
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, catID, flagged);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, catID);
                }

                //add entry to the list
                entryList.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return entryList;
    }

    /*
    return a list of entries ordered by the date with the same category ID
    or an empty list if there are no such entries
     */
    public List<Entry> getEntriesByCategoryID(int ID){
        Entry entry = null;
        ArrayList<Entry> listOfEntries = new ArrayList<Entry>();

        //get the default id of category
        IDManager manager = IDManagerFactory.createIDManager();
        int defaultCatID = manager.getDefaultID(ID_NAME_CAT);

        String sql = "SELECT * FROM " + ENTRY_TABLE + " WHERE " + CAT_ID + "=" + ID + " ORDER BY " + ENTRY_DATE; //prepare query

        //if id is null there is no such category, so check for null(default one)
        if(ID == defaultCatID){
            sql = "SELECT * FROM " + ENTRY_TABLE + " WHERE " + CAT_ID + " IS NULL" + " ORDER BY " + ENTRY_DATE; //prepare query
        }

        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        // getting all rows from cursor and filling the list
        if (cursor != null && cursor.getCount()> 0 && cursor.moveToFirst()) {
            do {

                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DETAILS)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));
                int type = cursor.getInt(cursor.getColumnIndex(ENTRY_TYPE));

                //check for purchase or income
                if(type == 0){
                    boolean flagged = 1 == cursor.getInt(cursor.getColumnIndex(ENTRY_FLAG));
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, ID, flagged);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, ID);
                }

                //add entry to the list
                listOfEntries.add(entry);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listOfEntries;
    }

    /*
    delete an entry and return true if the entry deleted successfully,
    otherwise return false
     */
    public boolean deleteEntry(int ID){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();

        // delete the entry with ID from the db
        int num = db.delete(ENTRY_TABLE,ENTRY_ID + "=" + ID, null);
        db.close();

        return num > 0;
    }

    /*
    Inserts a Category into the database.
    If the category with the same ID is in the db throws runtime exception
     */
    public void insertCategory(Category category){
        //get the datable
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int type = 0; //type of the db by default is purchase

        //check if a category is saving
        if (category instanceof SavingsCategory){
            type = 1;
        }

        //create a row of a new category
        values.put(CAT_ID, category.getID());
        values.put(CAT_NAME, category.getName().getValue());
        values.put(CAT_GOAL, category.getGoal().getValue());
        values.put(CAT_TYPE, type);

        //the date is in "yyyy-mm-dd" format
        String date = makeDate(category.getDateLastModified().getYear(), category.getDateLastModified().getMonth(), category.getDateLastModified().getDay());
        values.put(CAT_DATE, date);

        // insert to db
        long row = db.insert(CATEGORY_TABLE,null, values);

        //check for an error
        if(row == -1){
            db.close();
            throw new RuntimeException("The category you try to insert already exists in the database!");
        }else {
            db.close();
        }
    }

    /*
    Update the Category
    return true if the category is found in the database and then updated, otherwise return false
     */
    public boolean updateCategory(Category category){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //create a row of a new category
        values.put(CAT_ID, category.getID());
        values.put(CAT_NAME, category.getName().getValue());
        values.put(CAT_GOAL, category.getGoal().getValue());

        //the date is in "yyyy-mm-dd" format
        String date = makeDate(category.getDateLastModified().getYear(), category.getDateLastModified().getMonth(), category.getDateLastModified().getDay());
        values.put(CAT_DATE, date);

        // update this category in the db
        int num = db.update(CATEGORY_TABLE, values, CAT_ID + "=?",
                new String[]{String.valueOf(category.getID()) });
        db.close();

        return num > 0;
    }

    /*
    return a category by ID
    if not found returns null
     */
    public Category selectCategoryByID(int ID){
        Category category = null;
        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        String sql = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CAT_ID + "=" + ID; //prepare query

        Cursor cursor = db.rawQuery(sql, null); //execute query

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            //get all data from cursor to make a category object
            Amount goal = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(CAT_GOAL)));
            Details name = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(CAT_NAME)));
            Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(CAT_DATE)));
            int type = cursor.getInt(cursor.getColumnIndex(CAT_TYPE));

            //check for purchase or income
            if(type == 0){
                category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, ID);
            }else{
                category = SavingsCategoryFactory.createSavingsCategory(name, goal, date, ID);
            }

            cursor.close();
        }

        db.close();

        return category;
    }

    /*
    returns a list of all Categories ordered by date
    or an empty list if a category table is empty
     */
    public List<Category> getAllCategories(){
        Category category = null;
        List<Category> catList = new ArrayList<Category>();

        String sql = "SELECT * FROM " + CATEGORY_TABLE + " ORDER BY " + CAT_DATE; //prepare query

        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        // getting all rows from cursor and filling the list
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                //get all data from cursor to make a category object
                int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                Amount goal = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(CAT_GOAL)));
                Details name = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(CAT_NAME)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(CAT_DATE)));
                int type = cursor.getInt(cursor.getColumnIndex(CAT_TYPE));

                //check for purchase or income
                if(type == 0){
                    category = BudgetCategoryFactory.createBudgetCategory(name, goal, date, catID);
                }else{
                    category = SavingsCategoryFactory.createSavingsCategory(name, goal, date, catID);
                }

                //add category to the list
                catList.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return catList;
    }


    /*
    delete a category and return true if the category was deleted successfully,
    otherwise return false
     */
    public boolean deleteCategory(int ID){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();

        // delete a category with ID from the db
        int num = db.delete(CATEGORY_TABLE,CAT_ID + "=" + ID, null);
        db.close();

        return num > 0;
    }


    /*
    fills IDS_TABLE when the db is created
    */
    private void fillIDSWhenFirstCreated(int initialEntryID,int initialCategoryID){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + IDS_TABLE; //prepare query

        Cursor cursor = db.rawQuery(sql, null); //execute query

        //IDS table should be empty
        if (cursor != null && cursor.getCount() == 0) {

            //add some important info to db
            //fill ids table
            ContentValues values = new ContentValues();
            values.put(ID_NAME, ID_NAME_CAT);
            values.put(ID_NUM, initialCategoryID);
            db.insert(IDS_TABLE, null, values);

            values = new ContentValues();
            values.put(ID_NAME, ID_NAME_ENTRY);
            values.put(ID_NUM, initialEntryID);
            db.insert(IDS_TABLE, null, values);

            cursor.close();
        }

        db.close();
    }


    /*
     returns current entry id counter
     Possible idNames are "Entry" and "Category"
     returns -1 if no such id counter
     */
    public int getIDCounter(String idName){
        int idCounter = -1;
        SQLiteDatabase db = this.getReadableDatabase(); //connect to db

        String sql = "SELECT * FROM " + IDS_TABLE + " WHERE " + ID_NAME + "=" + "'" + idName +"'"; //prepare query

        Cursor cursor = db.rawQuery(sql, null); //execute query

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            //get all data from cursor
            idCounter = cursor.getInt(cursor.getColumnIndex(ID_NUM));
            cursor.close();
        }

        db.close();

        return idCounter;
    }


    /*
     updates entry id counter
     */
    public void updateIDCounter(String idName, int newCounter){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //create a row of a new counter
        values.put(ID_NAME, idName);
        values.put(ID_NUM, newCounter);

        // update this category in the db
        db.update(IDS_TABLE, values, ID_NAME + "=?",
                new String[]{String.valueOf(idName) });
        db.close();
    }


    /*
    deletes everything from tables in the db
     */
    public void clean(){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();

        // delete the entry with ID from the db
        db.delete(ENTRY_TABLE,null, null);
        db.delete(CATEGORY_TABLE,null, null);

        db.close();
    }
}
