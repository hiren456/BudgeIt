package com.codemonkeys9.budgeit.logiclayer.entryfetcher;

import com.codemonkeys9.budgeit.dso.dateintervel.DateInterval;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;

/*
This class is used by the other classes in the logic layer
to fetch EntryLists from the database that are
within a DateInterval
 */
public interface EntryFetcher {

    /*
    Fetches all Income Entries from the database that are within
    the dateInterval.
    Returns these entries as an EntryList object
     */
    EntryList fetchAllIncomeEntrys(DateInterval dateInterval);

    /*
    Fetches all Purchase Entries from the database that are within
    the dateInterval.
    Returns these entries as an EntryList object
     */
    EntryList fetchAllPurchasesEntrys(DateInterval dateInterval);

    /*
    Fetches all Entries from the database that are within
    the dateInterval.
    Returns these entries as an EntryList object
     */
    EntryList fetchAllEntrys(DateInterval dateInterval);
}