package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

public interface UIEntryFetcher {

    // Fetches all Income Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    EntryList fetchAllIncomeEntrys(String startDate, String endDate);

    // Fetches all Purchase Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    EntryList fetchAllPurchaseEntrys(String startDate, String endDate);

    // Fetches all Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    EntryList fetchAllEntrys(String startDate, String endDate);

    // These do the same as above except they get all
    // the entrys from the begining of time to the current date
    EntryList fetchAllIncomeEntrys();
    EntryList fetchAllPurchaseEntrys();
    EntryList fetchAllEntrys();
}
