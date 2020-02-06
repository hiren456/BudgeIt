package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.List;

import com.codemonkeys9.budgeit.entry.Entry;

public interface EntryFetcher {

    // Fetches all Income Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys(String startDate,String endDate);

    // Fetches all Purchase Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchasesEntrys(String startDate, String endDate);

    // Fetches all Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys(String startDate, String endDate);
}