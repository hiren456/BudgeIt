package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import java.util.List;

import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.entry.Entry;

/*
This class is fetches list of entries from the database
it is a class to be used purely by classes inside of the logic layer
because of this it takes date objects, not strings, and orders the
list returned in chronological order(same as the database)
 */
public interface EntryFetcher {

    // Fetches all Income Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllIncomeEntrys(DateInterval dateInterval);

    // Fetches all Purchase Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllPurchasesEntrys(DateInterval dateInterval);

    // Fetches all Entrys from startDate to endDate
    // Returns them in a list with the earliest date at position 0
    // and the latest date at the last position
    List<Entry> fetchAllEntrys(DateInterval dateInterval);
}