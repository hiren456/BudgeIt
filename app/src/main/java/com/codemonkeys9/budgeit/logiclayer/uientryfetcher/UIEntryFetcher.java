package com.codemonkeys9.budgeit.logiclayer.uientryfetcher;

import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateIntervalException;

public interface UIEntryFetcher {

    /*
    Fetches all the Income Entries from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllIncomeEntrys(String startDate, String endDate)
            throws InvalidDateException, InvalidDateIntervalException;

    /*
    Fetches all the Purchase Entries from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllPurchaseEntrys(String startDate, String endDate)
            throws InvalidDateException, InvalidDateIntervalException;

    /*
    Fetches all the Entries from
    startDate to endDate.
    startDate and endDate should be in "yyyy-mm-dd" format.
    If they are not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If the dates are valid but the startDate comes after, not on or before,
    the endDate then an InvalidDateIntervalException is thrown.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllEntrys(String startDate, String endDate)
            throws InvalidDateException, InvalidDateIntervalException;

    /*
    Fetches all the Income Entries from
    "1970-01-01 to the current date.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllIncomeEntrys();

    /*
    Fetches all the Purchase Entries from
    "1970-01-01 to the current date.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllPurchaseEntrys();

    /*
    Fetches all the Entries from
    "1970-01-01 to the current date.
    The entries are returned as an EntryList object
     */
    EntryList fetchAllEntrys();
}
