package com.codemonkeys9.budgeit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.dateinterval.DateInterval;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.IncomeFactory;
import com.codemonkeys9.budgeit.dso.entry.PurchaseFactory;

import java.util.ArrayList;
import java.util.List;

public class RealDatabase extends SQLiteOpenHelper implements Database {

    //db constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "budget.db";

    //db tables
    private static final String CATEGORY_TABLE = "categories";
    private static final String ENTRY_TABLE = "entries";

    //categories table attributes
    private static final String CAT_ID = "catID"; //also in entry table
    private static final String CAT_NAME = "catName";
    private static final String CAT_GOAL = "catGoal";
    private static final String CAT_DATE = "catDate";

    //entries table attributes
    private static final String ENTRY_ID = "entID";
    private static final String ENTRY_AMOUNT = "entAmount";
    private static final String ENTRY_DATE = "entDate";
    private static final String ENTRY_DETAILS = "entDetails";



    public RealDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String catCreateSQL =
                "CREATE TABLE " + CATEGORY_TABLE + " ( " +
                CAT_ID + " INTEGER PRIMARY KEY," + //primary key
                CAT_NAME + " TEXT, " +
                CAT_GOAL + " INTEGER, " +
                CAT_DATE + " TEXT" + " )";

        String entCreateSQL =
                "CREATE TABLE " + ENTRY_TABLE + " ( " +
                ENTRY_ID + " INTEGER PRIMARY KEY, " + //primary key
                CAT_ID + " INTEGER REFERENCES " + CATEGORY_TABLE + ", " + //foreign key
                ENTRY_AMOUNT + " INTEGER, " +
                ENTRY_DETAILS + " TEXT, " +
                ENTRY_DATE + " TEXT" + " )";

        db.execSQL(catCreateSQL);
        db.execSQL(entCreateSQL);
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

    //TODO insert category at first if entry without category
    /*
    Inserts an Entry into the database.
    If the Entry with the same ID is in the db throws runtime exception
     */
    public void insertEntry(Entry entry){
        //get the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //create a row of a new category
        values.put(ENTRY_ID, entry.getEntryID());
        values.put(CAT_ID, entry.getCatID());
        values.put(ENTRY_AMOUNT, entry.getAmount().getValue());
        values.put(ENTRY_DETAILS, entry.getDetails().getValue());

        //the date is in "yyyy-mm-dd" format
        String date = makeDate(entry.getDate().getYear(), entry.getDate().getMonth(), entry.getDate().getDay());
        values.put(ENTRY_DATE, date);

        // insert to db
        db.insert(ENTRY_TABLE,null, values);
        db.close();
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

        //create a row of a new category
        values.put(CAT_ID, entry.getCatID());
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

//        cursor.moveToFirst();
//        System.out.println("********");
//        System.out.println(cursor.getCount());
//        System.out.println("********");

        if (cursor != null) {
            cursor.moveToFirst();

            //get all data from cursor to make an entry object
            Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
            int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
            Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_AMOUNT)));
            Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));

            //check for purchase or income
            if(amount.getValue() < 0){
                entry = PurchaseFactory.createPurchase(amount, ID, details, date, catID);
            }else{
                entry = IncomeFactory.createIncome(amount, ID, details, date, catID);
            }

            cursor.close();
        }

        db.close();

        return entry;
    }

    /*
    return a list of all entries sorted by date
    or an empty list if db is empty
     */
    //TODO sort by date нужно ли?
    public List<Entry> getAllEntries(){
        Entry entry = null;
        ArrayList<Entry> entryList = new ArrayList<Entry>();

        String sql = "SELECT * FROM " + ENTRY_TABLE; //prepare query

        SQLiteDatabase db = this.getWritableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        // getting all rows from cursor and filling the list
        if (cursor.moveToFirst()) {
            do {
                //get all data from cursor to make an entry object
                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));

                //check for purchase or income
                if(amount.getValue() < 0){
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, catID);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, catID);
                }

                //add entry to the list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return entryList;
    }

    /*
    returns the list of entries from that fall within the dateInterval
    returns empty list if the are no entries
     */
    //TODO sort by date нужно ли?
    public List<Entry> selectByDate(DateInterval dateInterval){
        Entry entry = null;
        ArrayList<Entry> entryList = new ArrayList<Entry>();

        String sql = "SELECT * FROM " + ENTRY_TABLE; //prepare query

        SQLiteDatabase db = this.getWritableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        // getting all rows from cursor and filling the list
        if (cursor.moveToFirst()) {
            do {
                //get all data from cursor to make an entry object
                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));

                //check for purchase or income
                if(amount.getValue() < 0){
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, catID);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, catID);
                }

                //add entry to the list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        //TODO two ways to sort and get interval
        cursor.close();

        return entryList;
    }

    /*
    return a list of entries sorted by the date with the same category ID
    or an empty list if there are no such entries
     */
    public List<Entry> getEntriesByCategoryID(int ID){
        Entry entry = null;
        ArrayList<Entry> entryList = new ArrayList<Entry>();

        String sql = "SELECT * FROM " + ENTRY_TABLE + " WHERE " + CAT_ID + "=" + ID; //prepare query

        SQLiteDatabase db = this.getWritableDatabase(); //connect to db

        Cursor cursor = db.rawQuery(sql, null); //execute query

        // getting all rows from cursor and filling the list
        if (cursor.moveToFirst()) {
            do {
                //get all data from cursor to make an entry object
                int entID = cursor.getInt(cursor.getColumnIndex(ENTRY_ID));
                Amount amount = AmountFactory.fromInt(cursor.getInt(cursor.getColumnIndex(ENTRY_AMOUNT)));
                int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                Details details = DetailsFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_AMOUNT)));
                Date date = DateFactory.fromString(cursor.getString(cursor.getColumnIndex(ENTRY_DATE)));

                //check for purchase or income
                if(amount.getValue() < 0){
                    entry = PurchaseFactory.createPurchase(amount, entID, details, date, catID);
                }else{
                    entry = IncomeFactory.createIncome(amount, entID, details, date, catID);
                }

                //add entry to the list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return entryList;}

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

        return num > 0;}

    /*
    Inserts a Category into the database.
    If the category with the same ID is in the db throws runtime exception
     */
    public void insertCategory(Category category){
        //get the datable
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //create a row of a new category
        values.put(CAT_ID, category.getID());
        values.put(CAT_NAME, category.getName().getValue());
        values.put(CAT_GOAL, category.getGoal().getValue());
        values.put(CAT_DATE, "12081997");

        // insert to db
        db.insert(CATEGORY_TABLE,null, values);
        db.close();
    }

    /*
    Update the Category
    return true if the category is found in the database and then updated, otherwise return false
     */
    public boolean updateCategory(Category category){return true;}

    /*
    return a category by ID
    if not found returns null
     */
    public Category selectCategoryByID(int ID){return null;}

    /*
    returns a list of all Categories sorted by date
    or an empty list if db is empty
     */
    public List<Category> getAllCategories(){return null;}

    /*
    delete a category and return true if the category was deleted successfully,
    otherwise return false
     */
    public boolean deleteCategory(int ID){return true;}

    /*
     returns current entry id counter
     Possible idNames are "Entry" and "Category"
     */
    public int getIDCounter(String idName){return 0;}

    /*
     updates entry id counter
     */
    public void updateIDCounter(String idName, int newCounter){}
}
