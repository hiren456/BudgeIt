package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;

public interface UIEntryManager {
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
     */
    void createEntry(String amount, String details,String date,boolean purchase)
        throws InvalidDateException, InvalidAmountException;
    /*
    Takes either the id of an entry or the entry itself
    and updates its flag value with the value of flag.
    If the entry does not exist or is an income entry
    then an PurchaseDoesNotExistException
    will be thrown.
     */
    //void flagPurchase(int id,boolean flag)
    //        throws PurchaseDoesNotExistException;
    //void flagPurchase(Entry entry, boolean flag)
    //        throws PurchaseDoesNotExistException;
}
