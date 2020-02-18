package com.codemonkeys9.budgeit.logiclayer;

// TODO: Replace Java date with our date
import android.util.Pair;

import java.util.List;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.entry.Entry;

public interface LogicLayer {

    // Fetches all Income Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllIncomeEntrys(String  startDate, String endDate);

    // Fetches all Income Entrys from the default date
    // to the current date
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllIncomeEntrys();

    // Fetches all Purchase Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllPurchaseEntrys(String  startDate, String endDate);

    // Fetches all Purchase Entrys from the default date
    // to the current date
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllPurchaseEntrys();

    // Fetches all Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllEntrys(String  startDate, String  endDate);

    // Fetches all Entrys from a month from the dafault date
    // to the current date
    // Returns them in a list with the latest date at position 0
    // and the earliest date at the last position
    List<Entry> fetchAllEntrys();


    // Calculates the sum total of all Income entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non negative number
    Amount calculateTotalIncome(String startDate, String  endDate);

    // Calculates the sum total of all Income entered from
    // the default date to the current date
    // always returns a non negative number
    Amount calculateTotalIncome();

    // Calculates the sum total of all Purchases entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    Amount calculateTotalPurchases(String startDate, String endDate);

    // Calculates the sum total of all Purchases entered from
    // the default date to the current date
    // always returns a non positive number
    Amount calculateTotalPurchases();

    // Calculates the sum total of all Entrys entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    Amount calculateTotal(String startDate, String endDate);

    // Calculates the sum total of all Entrys entered from
    // the default date to the current date
    // always returns a non positive number
    Amount calculateTotal();

    // These method return a list of entrys returned by the
    // indicated name as well as their sum as in Integer
    // more could be added as the statistics needed
    // grows past just a sum
    Pair<List<Entry>,String> fetchIncomeDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,String>fetchPurchasesDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,String> fetchAllDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,String> fetchIncomeDisplayInfo();
    Pair<List<Entry>,String> fetchPurchasesDisplayInfo();
    Pair<List<Entry>,String> fetchAllDisplayInfo();


    // Creates and stores an entry
    void createEntry(String amount, String details,String date);

    // Shadow to allow bool representing bad purchase
    // void createEntry(String amount, String details,String date,boolean bad);

    // Shadow to allow int representing cat id
    // void createEntry(String amount, String details,String date,int catID);

    // Shadow to allow both the above parameter
    // void createEntry(String amount, String details,String date,boolean bad,int catID);

    // flag entry
    // Entry flagEntry(int entryID)

    // unflag entry
    // Entry unflagEntry(int entryID)

    //Create Category
    //void createCategory

    // delete Category
    //void deleteCategory(int catID)

    // Get all category
    // List<Category> getAllCategories

    // Get category display info
    // returns map with keys
    // "Entry List"
    // "Sum"
    // Map<String,Object> getCategoryDisplayInfo(int catID)

    // change cat name
    // void changeCatName

    // change cat goal
    // void changeCatGoal

    // Add Entry to to a category
    // void addEntryToCategory(int entryID,int catID)


}
