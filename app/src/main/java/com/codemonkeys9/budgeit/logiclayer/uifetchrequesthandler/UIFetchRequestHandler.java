package com.codemonkeys9.budgeit.logiclayer.uifetchrequesthandler;

import com.codemonkeys9.budgeit.entry.Entry;

import java.util.List;

public interface UIFetchRequestHandler {

    // Fetches all Income Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys(String startDate, String endDate);

    // Fetches all Purchase Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchaseEntrys(String startDate, String endDate);

    // Fetches all Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys(String startDate, String endDate);

    // These do the same as above except they get all
    // the entrys from the begining of time to the current date
    List<Entry> fetchAllIncomeEntrys();
    List<Entry> fetchAllPurchaseEntrys();
    List<Entry> fetchAllEntrys();
}
