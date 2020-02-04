package com.codemonkeys9.budgeit.LogicLayer;

// TODO: Replace Java date with our date
import android.util.Pair;
import android.util.StatsLog;

import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.Entry.Entry;

public interface LogicLayer {

    // Fetches all Income Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys(String  startDate, String endDate);

    // Fetches all Income Entrys from the default date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys();

    // Fetches all Purchase Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchaseEntrys(String  startDate, String endDate);

    // Fetches all Purchase Entrys from the default date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchaseEntrys();

    // Fetches all Entrys from startDate to endDate
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys(String  startDate, String  endDate);

    // Fetches all Entrys from a month from the dafault date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys();


    // Calculates the sum total of all Income entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non negative number
    int calculateTotalIncome(String startDate, String  endDate);

    // Calculates the sum total of all Income entered from
    // the default date to the current date
    // always returns a non negative number
    int calculateTotalIncome();

    // Calculates the sum total of all Purchases entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    int calculateTotalPurchases(String startDate, String endDate);

    // Calculates the sum total of all Purchases entered from
    // the default date to the current date
    // always returns a non positive number
    int calculateTotalPurchases();

    // Calculates the sum total of all Entrys entered from
    // startDate and endDate should be in "dd/mm/yyyy" format
    // passing "now" for endDate will use the current date as the endDate
    // the startDate to the endDate
    // always returns a non positive number
    int calculateTotal(String startDate, String endDate);

    // Calculates the sum total of all Entrys entered from
    // the default date to the current date
    // always returns a non positive number
    int calculateTotal();

    // These method return a list of entrys returned by the
    // indicated name as well as their sum as in Integer
    // more could be added as the statistics needed
    // grows past just a sum
    Pair<List<Entry>,Integer> fetchIncomeDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,Integer>fetchPurchasesDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,Integer> fetchAllDisplayInfo(String startDate, String endDate);
    Pair<List<Entry>,Integer> fetchIncomeDisplayInfo();
    Pair<List<Entry>,Integer> fetchPurchasesDisplayInfo();
    Pair<List<Entry>,Integer> fetchAllDisplayInfo();


    // Creates and stores an entry
    void createEntry(String amount, String details,String date);

}
