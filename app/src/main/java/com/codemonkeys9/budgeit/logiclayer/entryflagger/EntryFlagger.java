package com.codemonkeys9.budgeit.logiclayer.entryflagger;

import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;

public interface EntryFlagger {
    /*
    Takes either the id of an entry or the entry itself
    and updates its flag value with the value of flag.
    If the entry does not exist or is an income entry
    then an PurchaseDoesNotExistException
    will be thrown.
     */
    void flagPurchase(int id,boolean flag)
            throws PurchaseDoesNotExistException;
}
