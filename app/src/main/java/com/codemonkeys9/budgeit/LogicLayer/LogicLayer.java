package com.codemonkeys9.budgeit.LogicLayer;

// TODO: Replace Java date with our date
import java.util.Date;
import java.util.List;

import com.codemonkeys9.budgeit.Entry.Entry;

public interface LogicLayer {

    // Fetches all Income Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys(Date startDate, Date endDate);

    // Fetches all Income Entrys from the default date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys();

    // Fetches all Purchase Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchaseEntrys(Date startDate, Date endDate);

    // Fetches all Purchase Entrys from the default date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchaseEntrys();

    // Fetches all Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys(Date startDate, Date endDate);

    // Fetches all Entrys from a month from the dafault date
    // to the current date
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys();


    // Calculates the sum total of all Income entered from
    // the startDate to the endDate
    // always returns a non negative number
    int calculateTotalIncome(Date startDate, Date endDate);

    // Calculates the sum total of all Income entered from
    // the a month from the current date to the current date
    // always returns a non negative number
    //int calculateTotalIncome();

    // Calculates the sum total of all Purchases entered from
    // the startDate to the endDate
    // always returns a non positive number
    int calculateTotalPurchases(Date startDate, Date endDate);

    // Calculates the sum total of all Purchases entered from
    // the a month from the current date to the current date
    // always returns a non positive number
    //int calculateTotalPurchases();

    //List<Object> fetchIncomeDisplayInfo(Date startDate, Date endDate);


    // Creates and stores an entry
    void createEntry(String amount, String details,String date);

}
