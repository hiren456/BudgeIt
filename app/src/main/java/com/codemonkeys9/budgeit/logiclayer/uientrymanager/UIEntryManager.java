package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;

public interface UIEntryManager {

    /*
    Deletes an entry from the app(database).
    If the entry was not found in the database a
    EntryDoesNotExistException will be thrown
     */
    void deleteEntry(Entry entry)
            throws EntryDoesNotExistException;
    void deleteEntry(int entryID)
            throws EntryDoesNotExistException;

    /*
    Creates and stores an Entry object.
    The amount string should be in fixed point decimal.
    ie. using "99.99" as amount will result in an entry that
    has an Amount object with 99 dollars and 99 cents as its value.
    If the the amount is not in the proper format an InvalidAmountException
    will be thrown.
    "99.9" is valid and will be interpreted as "99.90".
    "99" is valid and will be interpreted as "99.00".
    All detail strings are valid.
    date should be in "yyyy-mm-dd" format.
    If it is not in this format or the date does not exist, ie.
    "2000-02-30", then an InvalidDateException will get throw.
    "2000-2-20" would be an invalid date because the month does not
    have two digits.
    If provided, the entry will be assigned to the catID category.
    If this category does not exist a CategoryDoesNotExistException will be thrown.
     */
    int createEntry(String amount, String details, String date, boolean purchase)
        throws InvalidDateException, InvalidAmountException, FutureDateException;
    int createEntry(String amount, String details, String date, boolean purchase,int catID)
            throws InvalidDateException, InvalidAmountException, FutureDateException, CategoryDoesNotExistException;
    /*
    Takes either the id of an entry or the entry itself
    and updates its flag value with the value of flag.
    If the entry does not exist or is an income entry
    then an PurchaseDoesNotExistException
    will be thrown.
     */
    void flagPurchase(int id,boolean flag)
            throws PurchaseDoesNotExistException;
    void flagPurchase(Entry entry, boolean flag)
            throws PurchaseDoesNotExistException;
}
