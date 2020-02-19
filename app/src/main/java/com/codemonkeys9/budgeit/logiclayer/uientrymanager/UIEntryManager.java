package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;

public interface UIEntryManager {
    void createEntry(String amount, String details,String date)
        throws InvalidDateException, InvalidAmountException;
}
